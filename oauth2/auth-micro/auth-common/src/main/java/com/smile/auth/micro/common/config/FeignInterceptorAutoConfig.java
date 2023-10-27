package com.smile.auth.micro.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @ClassName FeignInterceptorAutoConfig
 * @Author smile
 * @date 2023.10.21 06:13
 */
@Configuration
public class FeignInterceptorAutoConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        /**
         * 添加token
         */
        template.header("Authorization", request.getHeader("Authorization"));
    }
}
