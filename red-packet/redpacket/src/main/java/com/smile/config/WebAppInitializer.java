package com.smile.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @Description
 * @ClassName WebAppInitializer
 * @Author smile
 * @date 2023.04.08 16:51
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 依赖注入环境
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * DispatcherServlet环境配置
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * DispatchServlet拦截请求配置
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.html"};
    }
}
