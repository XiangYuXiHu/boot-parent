package com.smile.groovy.engine.core.executor;

import com.smile.groovy.engine.core.config.GroovyEngineProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

/**
 * 自动定时刷新脚本执行器
 *
 * @author smile
 */
public class AutoRefreshScriptExecutor implements SmartLifecycle {

    private Logger logger = LoggerFactory.getLogger(AutoRefreshScriptExecutor.class);
//    private final GroovyEngineProperties groovyEngineProperties;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
