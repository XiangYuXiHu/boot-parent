package com.smile.datasource.aop;

import com.smile.datasource.anno.DynamicDataSource;
import com.smile.datasource.core.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 动态路由切面
 *
 * @Description
 * @ClassName DynamicRoutingAspect
 * @Author smile
 * @date 2023.04.15 16:22
 */
@Aspect
public class DynamicRoutingAspect {

    @Around("@annotation(dynamicDataSource)")
    public Object routingwithDataSource(ProceedingJoinPoint joinPoint, DynamicDataSource dynamicDataSource) throws Throwable {
        try {
            DynamicDataSourceContextHolder.setDatasourceLookupKey(dynamicDataSource.value());
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDatasourceLookupKey();
        }
    }
}
