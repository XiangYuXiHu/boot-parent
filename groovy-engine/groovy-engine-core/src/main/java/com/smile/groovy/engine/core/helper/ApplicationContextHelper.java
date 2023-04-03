package com.smile.groovy.engine.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * spring 容器工具类
 *
 * @Description
 * @ClassName ApplicationContextHelper
 * @Author smile
 * @date 2023.04.01 22:04
 */
public class ApplicationContextHelper implements ApplicationContextAware {

    private static DefaultListableBeanFactory springFactory;
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.setContext(applicationContext);
        if (applicationContext instanceof AbstractRefreshableApplicationContext) {
            AbstractRefreshableApplicationContext springContext =
                    (AbstractRefreshableApplicationContext) applicationContext;
            ApplicationContextHelper.setSpringFactory((DefaultListableBeanFactory) springContext.getBeanFactory());
        } else if (applicationContext instanceof GenericApplicationContext) {
            GenericApplicationContext springContext = (GenericApplicationContext) applicationContext;
            ApplicationContextHelper.setSpringFactory(springContext.getDefaultListableBeanFactory());
        }
    }


    public static DefaultListableBeanFactory getSpringFactory() {
        return springFactory;
    }

    public static void setSpringFactory(DefaultListableBeanFactory springFactory) {
        ApplicationContextHelper.springFactory = springFactory;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        ApplicationContextHelper.context = context;
    }
}
