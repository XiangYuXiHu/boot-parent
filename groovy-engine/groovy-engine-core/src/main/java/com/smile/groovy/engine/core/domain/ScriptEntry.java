package com.smile.groovy.engine.core.domain;

import groovy.transform.EqualsAndHashCode;

/**
 * 脚本项，一条记录对应着一个脚本
 *
 * @Description
 * @ClassName ScriptEntry
 * @Author smile
 * @date 2023.04.01 19:26
 */
@EqualsAndHashCode
public class ScriptEntry {

    /**
     * 脚本名称
     */
    private String name;

    /**
     * 脚本内容
     */
    private String scriptContent;

    /**
     * 脚本指纹
     */
    private String fingerprint;

    /**
     * 最近修改时间
     */
    private Long lastModifiedTime;

    /**
     * 脚本code对应的class
     */
    private Class<?> clazz;

    public ScriptEntry(String name, String scriptContent, String fingerprint, Long lastModifiedTime) {
        this.name = name;
        this.scriptContent = scriptContent;
        this.fingerprint = fingerprint;
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "ScriptEntry{" +
                "name='" + name + '\'' +
                ", scriptContent='" + scriptContent + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                ", lastModifiedTime=" + lastModifiedTime +
                ", clazz=" + clazz +
                '}';
    }
}
