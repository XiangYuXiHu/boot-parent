package com.smile.rate.limit.aop;

import com.smile.rate.limit.anno.RateLimit;
import com.smile.rate.limit.exception.RateLimitException;
import com.smile.rate.limit.rule.KeyResolver;
import com.smile.rate.limit.support.DefaultLimitProperties;
import com.smile.rate.limit.support.LimitProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @ClassName RateLimitAspect
 * @Author smile
 * @date 2023.03.04 16:12
 */
@Slf4j
@Aspect
public class RateLimitAspect implements ApplicationContextAware {

    @Resource
    private RedisScript<List<Long>> rateLimitRedisScript;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ApplicationContext applicationContext;

    @Around("execution(public * *(..)) && @annotation(com.smile.rate.limit.anno.RateLimit)")
    public Object rateLimit(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        assertNonLimit(rateLimit, pjp);
        return pjp.proceed();
    }

    public void assertNonLimit(RateLimit rateLimit, ProceedingJoinPoint pjp) {
        Class<? extends KeyResolver> keyResolverClazz = rateLimit.keyResolver();
        KeyResolver keyResolver = applicationContext.getBean(keyResolverClazz);
        String uri = keyResolver.resolve(getCurrentRequest(), pjp);
        List<String> keys = getKeys(uri);
        LimitProperties limitProperties = getLimitProperties(rateLimit);
        // 根据限流时间维度计算时间
        long timeLong = getCurrentTimeLong(limitProperties.timeUnit());

        // The arguments to the LUA script. time() returns unixTime in seconds.
        List<String> scriptArgs = Arrays.asList(limitProperties.replenishRate() + "",
                limitProperties.burstCapacity() + "",
                (Instant.now().toEpochMilli() / timeLong) + "",
                "1", timeLong + "");

        List<Long> result = stringRedisTemplate.execute(rateLimitRedisScript, keys, scriptArgs.toArray());
        Long isAllowed = result.get(0);
        Long newTokens = result.get(1);
        log.info("rate limit key [{}] result: isAllowed [{}] new tokens [{}].", uri, isAllowed, newTokens);
        if (isAllowed <= 0) {
            throw new RateLimitException(uri);
        }

    }

    private List<String> getKeys(String id) {
        // use `{}` around keys to use Redis Key hash tags
        // this allows for using redis cluster

        // Make a unique key per user.
        String prefix = "request_rate_limiter.{" + id;

        // You need two Redis keys for Token Bucket.
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    private LimitProperties getLimitProperties(RateLimit rateLimit) {
        Class<? extends LimitProperties> aClass = rateLimit.limitProperties();
        if (aClass == DefaultLimitProperties.class) {
            return new DefaultLimitProperties(rateLimit.replenishRate(), rateLimit.burstCapacity(), rateLimit.timeUnit());
        }
        return applicationContext.getBean(aClass);
    }

    private long getCurrentTimeLong(TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                return 1000;
            case MINUTES:
                return 1000 * 60;
            case HOURS:
                return 1000 * 60 * 60;
            case DAYS:
                return 1000 * 60 * 60 * 24;
            default:
                throw new IllegalArgumentException("timeUnit:" + timeUnit + " not support");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取当前请求的Request对象
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
