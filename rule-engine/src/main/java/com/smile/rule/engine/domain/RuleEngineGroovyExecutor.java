package com.smile.rule.engine.domain;

/**
 * @Description
 * @ClassName RuleEngineGroovyExecutor
 * @Author smile
 * @date 2023.03.22 15:06
 */
public interface RuleEngineGroovyExecutor<T> {

    /**
     * 获取脚本执行
     *
     * @param name
     * @return
     */
    T getInstance(String name);

    /**
     * 编译并缓存
     *
     * @param name
     * @param script
     */
    void parseAndCache(String name, String script);
}
