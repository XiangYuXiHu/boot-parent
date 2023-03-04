package com.smile.rate.limit.rule;

import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 12780
 */
public interface KeyResolver {

    /**
     * 具体限流规则
     *
     * @param request
     * @param pjp
     * @return
     */
    String resolve(HttpServletRequest request, ProceedingJoinPoint pjp);
}
