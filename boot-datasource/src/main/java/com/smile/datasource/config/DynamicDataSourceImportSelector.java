package com.smile.datasource.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 动态数据源导入selector
 *
 * @Description
 * @ClassName DynamicDataSourceImportSelector
 * @Author smile
 * @date 2023.04.15 11:02
 */
public class DynamicDataSourceImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{DataSourceAutoConfiguration.class.getName()};
    }
}
