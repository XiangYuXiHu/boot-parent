package com.smile.groovy.classpath.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @ClassName GroovyClasspathLoaderProperties
 * @Author smile
 * @date 2023.04.05 11:05
 */
@ConfigurationProperties(prefix = GroovyClasspathLoaderProperties.PREFIX)
public class GroovyClasspathLoaderProperties {

    public static final String PREFIX = "groovy.engine.classpath-loader";

    /**
     * 读取groovy的脚本目录
     */
    private String directory;

    /**
     * 启基于 classpath 的脚本加载器
     */
    private boolean enable = false;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "GroovyClasspathLoaderProperties{" +
                "directory='" + directory + '\'' +
                ", enable=" + enable +
                '}';
    }
}
