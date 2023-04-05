package com.smile.groovy.engine.core.executor;

import com.smile.groovy.engine.core.domain.EngineExecutorResult;
import com.smile.groovy.engine.core.domain.ExecuteParams;
import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * 引擎执行器
 *
 * @author smile
 */
public interface EngineExecutor {

    /**
     * 执行脚本
     *
     * @param scriptQuery
     * @param executeParams
     * @return
     */
    @NonNull EngineExecutorResult execute(@NonNull ScriptQuery scriptQuery, ExecuteParams executeParams);

    /**
     * 执行脚本
     *
     * @param scriptEntry
     * @param executeParams
     * @return
     */
    @NonNull EngineExecutorResult execute(@NonNull ScriptEntry scriptEntry, ExecuteParams executeParams);

    /**
     * 根据groovy里的方法名来执行脚本方法
     *
     * @param groovyMethodName
     * @param scriptQuery
     * @param executeParams
     * @return
     */
    @NonNull EngineExecutorResult execute(@NonNull String groovyMethodName,
                                          @NonNull ScriptQuery scriptQuery,
                                          ExecuteParams executeParams);

    /**
     * 根据groovy里的方法名来执行脚本方法
     *
     * @param groovyMethodName
     * @param scriptEntry
     * @param executeParams
     * @return
     */
    @NonNull EngineExecutorResult execute(@NonNull String groovyMethodName,
                                          @NonNull ScriptEntry scriptEntry,
                                          ExecuteParams executeParams);
}
