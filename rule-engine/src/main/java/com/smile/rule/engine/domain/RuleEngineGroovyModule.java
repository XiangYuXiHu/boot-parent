package com.smile.rule.engine.domain;

/**
 * groovy执行的java接口
 *
 * @author 12780
 */
public interface RuleEngineGroovyModule {

    /**
     * 执行
     *
     * @param context
     * @return
     */
    boolean run(RuleEngineExecuteContext context);
}
