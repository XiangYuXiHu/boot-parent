package com.smile.datasource.creator;

import com.smile.datasource.config.properties.DataSourceProperty;
import com.smile.datasource.config.properties.HikariCpConfig;
import com.smile.datasource.exception.DynamicDataSourceException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * @Description
 * @ClassName DynamicDataSourceCreator
 * @Author smile
 * @date 2023.04.15 15:27
 */
public class DynamicDataSourceCreator {

    /**
     * HikariCp数据源
     */
    private static final String HIKARI_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";
    /**
     * 是否存在hikari
     */
    private Boolean hikariExists = false;

    private HikariCpConfig hikariGlobalConfig;


    public DynamicDataSourceCreator() {
        try {
            Class.forName(HIKARI_DATASOURCE);
            hikariExists = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据源
     *
     * @param dataSourceProperty
     * @return
     */
    public DataSource createDataSource(DataSourceProperty dataSourceProperty) {
        Class<? extends DataSource> type = dataSourceProperty.getType();
        if (null == type) {
            if (hikariExists) {
                return createHikariDataSource(dataSourceProperty);
            } else {
                throw new DynamicDataSourceException("create datasource occur error, please check the data source dependencies.");
            }
        } else if (HIKARI_DATASOURCE.equalsIgnoreCase(type.getName())) {
            return createHikariDataSource(dataSourceProperty);
        } else {
            throw new DynamicDataSourceException("create datasource occur error, unsupported data source type.");
        }
    }

    public DataSource createHikariDataSource(DataSourceProperty dataSourceProperty) {
        HikariCpConfig hikariCpConfig = dataSourceProperty.getHikari();
        HikariConfig hikariConfig = hikariCpConfig.toHikariConfig(hikariGlobalConfig);
        hikariConfig.setUsername(dataSourceProperty.getUsername());
        hikariConfig.setPassword(dataSourceProperty.getPassword());
        hikariConfig.setJdbcUrl(dataSourceProperty.getUrl());
        hikariConfig.setDriverClassName(dataSourceProperty.getDriverClassName());
        hikariConfig.setPoolName(dataSourceProperty.getPollName());
        return new HikariDataSource(hikariConfig);
    }

    public Boolean getHikariExists() {
        return hikariExists;
    }

    public void setHikariExists(Boolean hikariExists) {
        this.hikariExists = hikariExists;
    }

    public HikariCpConfig getHikariGlobalConfig() {
        return hikariGlobalConfig;
    }

    public void setHikariGlobalConfig(HikariCpConfig hikariGlobalConfig) {
        this.hikariGlobalConfig = hikariGlobalConfig;
    }
}
