package com.smile.groovy.engine.core.executor;

import com.google.common.base.Preconditions;
import com.smile.groovy.engine.core.constants.GroovyEngineConstants;
import com.smile.groovy.engine.core.domain.EngineExecutorResult;
import com.smile.groovy.engine.core.domain.ExecutorParams;
import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import com.smile.groovy.engine.core.helper.ApplicationContextHelper;
import com.smile.groovy.engine.core.registry.ScriptRegistry;
import groovy.lang.Binding;
import groovy.lang.Script;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * 默认引擎执行器
 *
 * @Description
 * @ClassName DefaultEngineExecutor
 * @Author smile
 * @date 2023.04.02 21:35
 */
public class DefaultEngineExecutor implements EngineExecutor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScriptRegistry scriptRegistry;

    public DefaultEngineExecutor(ScriptRegistry scriptRegistry) {
        this.scriptRegistry = scriptRegistry;
    }

    @Override
    public @NonNull EngineExecutorResult execute(@NonNull ScriptQuery scriptQuery, ExecutorParams executorParams) {
        ScriptEntry scriptEntry = null;
        try {
            scriptEntry = scriptRegistry.find(scriptQuery);
        } catch (Exception e) {
            EngineExecutorResult.failed(e.getMessage());
        }
        return execute(scriptEntry, executorParams);
    }

    @Override
    public @NonNull EngineExecutorResult execute(@NonNull ScriptEntry scriptEntry, ExecutorParams executorParams) {
        Binding binding = buildBinding(executorParams);
        Preconditions.checkNotNull(scriptEntry.getClazz(), "execute script failed, clazz can not be null");
        Script script = InvokerHelper.createScript(scriptEntry.getClazz(), binding);
        script.setBinding(binding);
        Object result = script.run();
        return EngineExecutorResult.success(result);
    }

    @Override
    public @NonNull EngineExecutorResult execute(@NonNull String groovyMethodName, @NonNull ScriptQuery scriptQuery, ExecutorParams executorParams) {
        ScriptEntry scriptEntry = null;
        try {
            scriptEntry = scriptRegistry.find(scriptQuery);
        } catch (Exception e) {
            EngineExecutorResult.failed(e.getMessage());
        }
        return execute(groovyMethodName, scriptEntry, executorParams);
    }

    @Override
    public @NonNull EngineExecutorResult execute(@NonNull String groovyMethodName, @NonNull ScriptEntry scriptEntry, ExecutorParams executorParams) {
        Binding binding = buildBinding(executorParams);
        Preconditions.checkNotNull(scriptEntry.getClazz(), "execute script failed, clazz can not be null");
        Script script = InvokerHelper.createScript(scriptEntry.getClazz(), binding);
        Object result = script.invokeMethod(groovyMethodName, executorParams);
        return EngineExecutorResult.success(result);
    }

    /**
     * 构建bind
     *
     * @param params
     * @return
     */
    private Binding buildBinding(ExecutorParams params) {
        Binding binding = new Binding();
        /**
         * 将spring容器上下文放入脚本
         */
        binding.setProperty(GroovyEngineConstants.APPLICATION_CONTEXT, ApplicationContextHelper.getContext());
        if (CollectionUtils.isEmpty(params)) {
            return binding;
        }
        params.forEach(binding::setProperty);
        return binding;
    }
}
