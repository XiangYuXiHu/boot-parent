package com.smile.groovy.engine.core.helper;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

/**
 * 手动注册脚本助手
 *
 * @author smile
 */
public interface RegisterScriptHelper {

    /**
     * 注册groovy脚本
     *
     * @param name
     * @param content
     * @param allowCover
     * @return
     * @throws Exception
     */
    boolean registerScript(@NonNull String name, @NonNull String content, boolean allowCover) throws Exception;

    /**
     * 批量注册groovy脚本，key为脚本名称，value 为脚本内容
     *
     * @param scriptMap
     * @param allowCover
     * @return
     * @throws Exception
     */
    boolean batchRegisterScript(Map<String, String> scriptMap, boolean allowCover) throws Exception;
}
