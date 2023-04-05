package com.smile.groovy.engine.core.executor;

import com.smile.groovy.engine.core.config.GroovyEngineProperties;
import com.smile.groovy.engine.core.helper.RefreshScriptHelper;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自动定时刷新脚本执行器
 *
 * @author smile
 */
public class AutoRefreshScriptExecutor implements SmartLifecycle {

    private Logger logger = LoggerFactory.getLogger(AutoRefreshScriptExecutor.class);
    private final GroovyEngineProperties groovyEngineProperties;
    private final RefreshScriptHelper refreshScriptHelper;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public AutoRefreshScriptExecutor(GroovyEngineProperties groovyEngineProperties,
                                     RefreshScriptHelper refreshScriptHelper) {
        this.groovyEngineProperties = groovyEngineProperties;
        this.refreshScriptHelper = refreshScriptHelper;
    }

    @Override
    public void start() {
        if (!isRunning.compareAndSet(false, true)) {
            logger.error("Note AutoRefreshScriptExecutor already started, skip.");
            return;
        }
        logger.info("AutoRefreshScriptExecutor thread start.");
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("groovy-engine-executor").daemon(true)
                .build());
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::refreshScript, groovyEngineProperties.getInitialDelay(),
                groovyEngineProperties.getPollingCycle(), TimeUnit.SECONDS);
        logger.info("AutoRefreshScriptExecutor thread complete.");
    }

    /**
     * 容器关闭后，spring容器发现当前对象实现了SmartLifecycle，
     * 就调用stop(Runnable)，如果只是实现了Lifecycle，就调用stop()
     */
    @Override
    public void stop() {
        logger.warn("container is stopping, stop auto refresh script now.");
        isRunning.compareAndSet(true, false);
    }

    /**
     * // 组件是否在运行中
     *
     * @return
     */
    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    /**
     * 当容器没有启动过时才吊起start
     *
     * @return
     */
    @Override
    public boolean isAutoStartup() {
        return !isRunning.get();
    }

    private void refreshScript() {
        if (isRunning.get()) {
            refreshScriptHelper.refreshAll();
        } else {
            logger.warn("can not refresh script because isRunning status is false.");
        }
    }
}
