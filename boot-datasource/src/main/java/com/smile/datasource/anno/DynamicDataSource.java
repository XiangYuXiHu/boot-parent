package com.smile.datasource.anno;

import java.lang.annotation.*;

/**
 * 动态数据源注解
 *
 * @author smile
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicDataSource {

    /**
     * 数据源名称
     *
     * @return
     */
    String value();
}
