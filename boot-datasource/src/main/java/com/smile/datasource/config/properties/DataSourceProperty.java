package com.smile.datasource.config.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.sql.DataSource;

/**
 * 数据源基础配置
 *
 * @Description
 * @ClassName DataSourceProperty
 * @Author smile
 * @date 2023.04.15 11:04
 */
public class DataSourceProperty {

    /**
     * 连接池名称(只是一个名称标识)</br>
     * 默认是配置文件上的名称
     */
    private String pollName;

    /**
     * 连接池类型，如果不设置自动查找 Druid > HikariCp
     */
    private Class<? extends DataSource> type;

    /**
     * JDBC driver
     */
    private String driverClassName;

    /**
     * JDBC url 地址
     */
    private String url;

    private String username;

    private String password;

    @NestedConfigurationProperty
    private HikariCpConfig hikari = new HikariCpConfig();


    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public Class<? extends DataSource> getType() {
        return type;
    }

    public void setType(Class<? extends DataSource> type) {
        this.type = type;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HikariCpConfig getHikari() {
        return hikari;
    }

    public void setHikari(HikariCpConfig hikari) {
        this.hikari = hikari;
    }

    @Override
    public String toString() {
        return "DataSourceProperty{" +
                "pollName='" + pollName + '\'' +
                ", type=" + type +
                ", driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
