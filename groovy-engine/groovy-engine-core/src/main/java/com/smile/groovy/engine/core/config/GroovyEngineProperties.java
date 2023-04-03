package com.smile.groovy.engine.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @ClassName GroovyEngineProperties
 * @Author smile
 * @date 2023.04.01 19:48
 */
@ConfigurationProperties(prefix = GroovyEngineProperties.PREFIX)
public class GroovyEngineProperties {

    public static final String PREFIX = "groovy.engine";

    /**
     * 轮询检查脚本变更时间周期，单位：秒
     */
    private Long pollingCycle = 300L;

    /**
     * 初次轮询检查脚本变更延时时间L
     */
    private Long initialDelay = 0L;

    /**
     * 是否开启groovy脚本引擎功能，默认不开启
     */
    private boolean enable = false;

    /**
     * 本地缓存失效时间(单位：分钟)，默认600分钟
     */
    private Long cacheExpireAfterWrite = 600L;

    /**
     * 本地缓存初始容量，默认500
     */
    private Integer cacheInitialCapacity = 500;

    /**
     * 本地缓存最大容量，默认800
     */
    private Long cacheMaximumSize = 1000L;

    public Long getPollingCycle() {
        return pollingCycle;
    }

    public void setPollingCycle(Long pollingCycle) {
        this.pollingCycle = pollingCycle;
    }

    public Long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Long getCacheExpireAfterWrite() {
        return cacheExpireAfterWrite;
    }

    public void setCacheExpireAfterWrite(Long cacheExpireAfterWrite) {
        this.cacheExpireAfterWrite = cacheExpireAfterWrite;
    }

    public Integer getCacheInitialCapacity() {
        return cacheInitialCapacity;
    }

    public void setCacheInitialCapacity(Integer cacheInitialCapacity) {
        this.cacheInitialCapacity = cacheInitialCapacity;
    }

    public Long getCacheMaximumSize() {
        return cacheMaximumSize;
    }

    public void setCacheMaximumSize(Long cacheMaximumSize) {
        this.cacheMaximumSize = cacheMaximumSize;
    }

    @Override
    public String toString() {
        return "GroovyEngineProperties{" +
                "pollingCycle=" + pollingCycle +
                ", initialDelay=" + initialDelay +
                ", enable=" + enable +
                ", cacheExpireAfterWrite=" + cacheExpireAfterWrite +
                ", cacheInitialCapacity=" + cacheInitialCapacity +
                ", cacheMaximumSize=" + cacheMaximumSize +
                '}';
    }
}
