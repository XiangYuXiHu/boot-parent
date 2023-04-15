package com.smile.datasource.config.properties;

import com.zaxxer.hikari.HikariConfig;

import java.lang.reflect.Field;
import java.util.Properties;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Description
 * @ClassName HikariCpConfig
 * @Author smile
 * @date 2023.04.15 11:29
 */
public class HikariCpConfig {

    private static final Long CONNECTION_TIMEOUT = SECONDS.toMillis(30);
    private static final Long VALIDATION_TIMEOUT = SECONDS.toMillis(5);
    private static final Long IDLE_TIMEOUT = MINUTES.toMillis(10);
    private static final Long MAX_LIFETIME = MINUTES.toMillis(30);
    private static final int DEFAULT_POOL_SIZE = 10;

    /**
     * 表示连接池的用户定义名称，主要出现在日志记录和JMX管理控制台中以识别池和池配置
     */
    private String poolName;
    private String username;
    private String password;
    private String driverClassName;
    private String jdbcUrl;

    /**
     * 属性设置默认目录为支持目录的概念数据库。如果未指定此属性，
     * 则使用由JDBC驱动程序定义的默认目录。 默认：驱动程序默认
     */
    private String catalog;
    /**
     * 属性控制客户端将等待来自池的连接的最大毫秒数
     */
    private Long connectionTimeout;
    /**
     * 属性控制连接测试活动的最长时间。这个值必须小于connectionTimeout。
     * 最低可接受的验证超时时间为250 ms。 默认值：5000
     */
    private Long validationTimeout;
    /**
     * 属性控制允许连接在池中闲置的最长时间
     */
    private Long idleTimeout;
    /**
     * 属性控制在记录消息之前连接可能离开池的时间量，表明可能存在连接泄漏。
     * 值为0意味着泄漏检测被禁用。启用泄漏检测的最低可接受值为2000(2秒)。 默认值：0
     */
    private Long leakDetectionThreshold;
    /**
     * 属性控制池中连接的最大生存期
     */
    private Long maxLifetime;
    private Integer maxPoolSize;
    private Integer minIdle;
    /**
     * 该属性设置一个SQL语句，在将每个新连接创建后，将其添加到池中之前执行该语句
     */
    private String connectionInitSql;
    private String connectionTestQuery;
    private String dataSourceClassName;
    /**
     * 该属性设置的默认模式为支持模式的概念数据库。如果未指定此属性，
     * 则使用由JDBC驱动程序定义的默认模式。 默认：驱动程序默认
     */
    private String schema;
    /**
     * 自动提交事务, 默认值true
     */
    private Boolean isAutoCommit;
    /**
     * 控制从池中获取的连接是否是只读模式, 需要数据库支持只读模式
     */
    private Boolean isReadOnly;
    private Properties dataSourceProperties;
    private Properties healthCheckProperties;

    public HikariConfig toHikariConfig(HikariCpConfig globalConfig) {
        HikariConfig config = new HikariConfig();
        String tmpSchema = schema == null ? globalConfig.getSchema() : schema;
        if (tmpSchema != null) {
            try {
                Field field = HikariConfig.class.getDeclaredField("schema");
                field.setAccessible(true);
                field.set(config, tmpSchema);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String tmpCatalog = catalog == null ? globalConfig.getCatalog() : catalog;
        if (tmpCatalog != null) {
            config.setCatalog(tmpCatalog);
        }

        Long tmpConnectionTimeout = connectionTimeout == null ? globalConfig.getConnectionTimeout() : connectionTimeout;
        if (null != tmpConnectionTimeout && !tmpConnectionTimeout.equals(CONNECTION_TIMEOUT)) {
            config.setConnectionTimeout(tmpConnectionTimeout);
        }

        Long tempValidationTimeout = validationTimeout == null ? globalConfig.getValidationTimeout() : validationTimeout;
        if (tempValidationTimeout != null && !tempValidationTimeout.equals(VALIDATION_TIMEOUT)) {
            config.setValidationTimeout(tempValidationTimeout);
        }

        Long tempIdleTimeout = idleTimeout == null ? globalConfig.getIdleTimeout() : idleTimeout;
        if (tempIdleTimeout != null && !tempIdleTimeout.equals(IDLE_TIMEOUT)) {
            config.setIdleTimeout(tempIdleTimeout);
        }

        Long tempMaxLifetime = maxLifetime == null ? globalConfig.getMaxLifetime() : maxLifetime;
        if (tempMaxLifetime != null && !tempMaxLifetime.equals(MAX_LIFETIME)) {
            config.setMaxLifetime(tempMaxLifetime);
        }

        Integer tempMaxPoolSize = maxPoolSize == null ? globalConfig.getMaxPoolSize() : maxPoolSize;
        if (tempMaxPoolSize != null && !tempMaxPoolSize.equals(-1)) {
            config.setMaximumPoolSize(tempMaxPoolSize);
        }

        Integer tempMinIdle = minIdle == null ? globalConfig.getMinIdle() : getMinIdle();
        if (tempMinIdle != null && !tempMinIdle.equals(-1)) {
            config.setMinimumIdle(tempMinIdle);
        }

        String tempConnectionInitSql = connectionInitSql == null ? globalConfig.getConnectionInitSql() : connectionInitSql;
        if (tempConnectionInitSql != null) {
            config.setConnectionInitSql(tempConnectionInitSql);
        }

        String tempConnectionTestQuery = connectionTestQuery == null ? globalConfig.getConnectionTestQuery() : connectionTestQuery;
        if (tempConnectionTestQuery != null) {
            config.setConnectionTestQuery(tempConnectionTestQuery);
        }

        String tempDataSourceClassName = dataSourceClassName == null ? globalConfig.getDataSourceClassName() : dataSourceClassName;
        if (tempDataSourceClassName != null) {
            config.setDataSourceClassName(tempDataSourceClassName);
        }

        Boolean tempAutoCommit = isAutoCommit == null ? globalConfig.getAutoCommit() : isAutoCommit;
        if (tempAutoCommit != null && tempAutoCommit.equals(Boolean.FALSE)) {
            config.setAutoCommit(false);
        }

        Boolean tempReadOnly = isReadOnly == null ? globalConfig.getReadOnly() : isReadOnly;
        if (tempReadOnly != null) {
            config.setReadOnly(tempReadOnly);
        }

        Properties tempDataSourceProperties = dataSourceProperties == null ? globalConfig.getDataSourceProperties() : dataSourceProperties;
        if (tempDataSourceProperties != null) {
            config.setDataSourceProperties(tempDataSourceProperties);
        }

        Properties tempHealthCheckProperties = healthCheckProperties == null ? globalConfig.getHealthCheckProperties() : healthCheckProperties;
        if (tempHealthCheckProperties != null) {
            config.setHealthCheckProperties(tempHealthCheckProperties);
        }

        return config;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
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

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Long getValidationTimeout() {
        return validationTimeout;
    }

    public void setValidationTimeout(Long validationTimeout) {
        this.validationTimeout = validationTimeout;
    }

    public Long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Long getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(Long leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }

    public Long getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public String getConnectionInitSql() {
        return connectionInitSql;
    }

    public void setConnectionInitSql(String connectionInitSql) {
        this.connectionInitSql = connectionInitSql;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    public void setDataSourceClassName(String dataSourceClassName) {
        this.dataSourceClassName = dataSourceClassName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Boolean getAutoCommit() {
        return isAutoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        isAutoCommit = autoCommit;
    }

    public Boolean getReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        isReadOnly = readOnly;
    }

    public Properties getDataSourceProperties() {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(Properties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public Properties getHealthCheckProperties() {
        return healthCheckProperties;
    }

    public void setHealthCheckProperties(Properties healthCheckProperties) {
        this.healthCheckProperties = healthCheckProperties;
    }
}
