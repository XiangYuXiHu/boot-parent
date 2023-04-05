package com.smile.groovy.engine.core.domain;

import java.util.HashMap;

/**
 * @Description
 * @ClassName ExecutorParam
 * @Author smile
 * @date 2023.04.02 21:13
 */
public class ExecuteParams extends HashMap<String, Object> {

    /**
     * 通过key获取value，并转换为对应的类型
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getValue(String key) {
        return (T) get(key);
    }
}
