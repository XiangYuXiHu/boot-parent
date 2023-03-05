package com.smile.repeat.aop;


import com.google.common.collect.Lists;
import com.smile.repeat.Service.RedisService;
import com.smile.repeat.anno.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 注解添加在controller方法上
 *
 * @author zhangzhicheng
 * @version 1.0.0
 * @createTime 2020年12月10日 16:37:00
 */
@Slf4j
@Aspect
@Component
public class DistributedLocksAspect {

    @Autowired
    private RedisService redisService;

    private Map<String, List<Method>> cacheMethods = new HashMap<>();

    @Around("@annotation(distributedLock)")
    public Object doAround(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {

        Boolean isLock = false;
        Object result = null;
        StringBuilder strBuilder = new StringBuilder();

        try {
            if (distributedLock != null && distributedLock.enable()) {
                Class<?> clazz = distributedLock.clazzType();
                String clazzSimpleName = clazz.getSimpleName();
                List<Method> clazzMethods = cacheMethods.get(clazzSimpleName);
                String methodName = distributedLock.methodName();
                int retry = distributedLock.retry();
                int timeout = distributedLock.expire();
                String keyPrefix = distributedLock.prefix();
                String delimiter = distributedLock.delimiter();

                List<Method> methods = null;
                if (CollectionUtils.isEmpty(clazzMethods)) {
                    methods = Lists.newArrayList(clazz.getMethods());
                    cacheMethods.put(clazzSimpleName, methods);
                } else {
                    methods = cacheMethods.get(clazzSimpleName);
                }
                Optional<Method> executeMethod = methods.stream().filter(m -> m.getName().equalsIgnoreCase(methodName)).findFirst();
                if (executeMethod.isPresent() == Boolean.FALSE) {
                    throw new RuntimeException("产生分布式锁的key的方法不存在!");
                }
                Object[] args = joinPoint.getArgs();
                executeMethod.get().setAccessible(true);
                String key = (String) executeMethod.get().invoke(null, args);
                log.info("产生分布式键的方法:{}执行参数:{},产生的键为:{}", executeMethod.get().getName(), args, key);
                if (StringUtils.isNotBlank(keyPrefix)) {
                    strBuilder.append(keyPrefix).append(delimiter);
                }
                if (StringUtils.isNotBlank(key)) {
                    strBuilder.append(key);
                    isLock = redisService.tryLock(strBuilder.toString(), null, retry, timeout);
                }
            }
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            throw ex;
        } finally {
            if (isLock && StringUtils.isNotBlank(strBuilder.toString())) {
                redisService.release(strBuilder.toString(), null);
            }
        }
        return result;
    }
}
