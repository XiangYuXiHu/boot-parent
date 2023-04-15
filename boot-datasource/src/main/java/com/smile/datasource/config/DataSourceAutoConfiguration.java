package com.smile.datasource.config;

import com.smile.datasource.anno.DynamicDataSource;
import com.smile.datasource.aop.DynamicRoutingAspect;
import com.smile.datasource.config.properties.DynamicDataSourceProperties;
import com.smile.datasource.core.DynamicRoutingDataSource;
import com.smile.datasource.creator.DynamicDataSourceCreator;
import com.smile.datasource.provider.DynamicDataSourceProvider;
import com.smile.datasource.provider.YmlDynamicDataSourceProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Description
 * @ClassName DataSourceAutoConfiguration
 * @Author smile
 * @date 2023.04.15 11:03
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DataSourceAutoConfiguration {

    @Bean
    public DynamicRoutingAspect dynamicRoutingAspect() {
        return new DynamicRoutingAspect();
    }

    @Bean
    public DynamicDataSourceCreator dynamicDataSourceCreator(DynamicDataSourceProperties properties) {
        DynamicDataSourceCreator dynamicDataSourceCreator = new DynamicDataSourceCreator();
        dynamicDataSourceCreator.setHikariGlobalConfig(properties.getHikari());
        return dynamicDataSourceCreator;
    }

    @Bean
    @ConditionalOnMissingBean(DynamicDataSourceProvider.class)
    public DynamicDataSourceProvider ymlDynamicDataSourceProvider(DynamicDataSourceProperties properties,
                                                                  DynamicDataSourceCreator dynamicDataSourceCreator) {
        return new YmlDynamicDataSourceProvider(properties, dynamicDataSourceCreator);
    }


    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider,
                                 DynamicDataSourceProperties dynamicDataSourceProperties) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setPrimary(dynamicDataSourceProperties.getPrimary());
        dataSource.setProvider(dynamicDataSourceProvider);
        dataSource.init();
        return dataSource;
    }


}
