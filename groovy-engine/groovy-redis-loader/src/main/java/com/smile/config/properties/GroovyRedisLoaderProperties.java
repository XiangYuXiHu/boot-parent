package com.smile.config.properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import static com.smile.config.properties.GroovyRedisLoaderProperties.PREFIX;

/**
 * @Description
 * @ClassName GroovyRedisLoaderProperties
 * @Author smile
 * @date 2023.04.05 19:22
 */
@ConfigurationProperties(prefix = PREFIX)
public class GroovyRedisLoaderProperties implements InitializingBean, EnvironmentAware {

    public static final String PREFIX = "groovy.engine.redis-loader";
    /**
     * 环境信息
     */
    private Environment environment;
    /**
     * 脚本组，以 namespace 来区分不同的应用，同时在Redis里也能够按服务来区分脚本方便查看管理
     * (该值一般和应用名称保持一致即可)
     */
    private String namespace;

    /**
     * 开启基于 Redis 的脚本加载器
     */
    private boolean enable = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(namespace)) {
            namespace = environment.getProperty("spring.application.name");
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
