package com.smile.rate.limit.rule;

import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * 根据请求的Uri进行限流
 *
 * @Description
 * @ClassName UriKeyResolver
 * @Author smile
 * @date 2023.03.04 15:02
 */
public class UriKeyResolver implements KeyResolver {
    @Override
    public String resolve(HttpServletRequest request, ProceedingJoinPoint pjp) {
        return request.getRequestURI();
    }
}
