package com.smile.datasource.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName DynamicDataSourceProperites
 * @Author smile
 * @date 2023.04.15 11:13
 */
@ConfigurationProperties(prefix = "spring.datasource.dynamic")
public class DynamicDataSourceProperties {

    /**
     * 必须设置默认的库,默认master
     */
    private String primary = "master";
    /**
     * 每一个数据源
     */
    private Map<String, DataSourceProperty> datasource = new LinkedHashMap<>();

    /**
     * Hikari全局参数配置
     */
    @NestedConfigurationProperty
    private HikariCpConfig hikari = new HikariCpConfig();

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public Map<String, DataSourceProperty> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DataSourceProperty> datasource) {
        this.datasource = datasource;
    }

    public HikariCpConfig getHikari() {
        return hikari;
    }

    public void setHikari(HikariCpConfig hikari) {
        this.hikari = hikari;
    }
}
