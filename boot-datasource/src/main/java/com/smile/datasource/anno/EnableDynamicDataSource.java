package com.smile.datasource.anno;

import com.smile.datasource.config.DynamicDataSourceImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description
 * @ClassName EnableDynamicDataSource
 * @Author smile
 * @date 2023.04.15 14:29
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(DynamicDataSourceImportSelector.class)
public @interface EnableDynamicDataSource {
}
