package com.smile.groovy.engine.core.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.smile.groovy.engine.core.alarm.HotLoadingGroovyScriptAlarm;
import com.smile.groovy.engine.core.compiler.DynamicCodeCompiler;
import com.smile.groovy.engine.core.compiler.GroovyCompiler;
import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.executor.DefaultEngineExecutor;
import com.smile.groovy.engine.core.executor.EngineExecutor;
import com.smile.groovy.engine.core.helper.ApplicationContextHelper;
import com.smile.groovy.engine.core.loader.ScriptLoader;
import com.smile.groovy.engine.core.registry.DefaultScriptRegistry;
import com.smile.groovy.engine.core.registry.ScriptRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 自动配置
 *
 * @Description
 * @ClassName GroovyEngineCoreAutoConfiguration
 * @Author smile
 * @date 2023.04.01 19:39
 */
@Configuration
@EnableConfigurationProperties(value = {GroovyEngineProperties.class})
@ConditionalOnProperty(prefix = GroovyEngineProperties.PREFIX + ".enable", havingValue = "true")
public class GroovyEngineCoreAutoConfiguration {

    private Logger log = LoggerFactory.getLogger(GroovyEngineCoreAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(DynamicCodeCompiler.class)
    public DynamicCodeCompiler dynamicCodeCompiler() {
        return new GroovyCompiler();
    }


    @Bean(name = "groovyScriptEngineCache")
    @ConditionalOnMissingBean(name = "groovyScriptEngineCache", value = {Cache.class})
    public Cache<String, ScriptEntry> groovyScriptEngineCache(GroovyEngineProperties groovyEngineProperties) {
        return Caffeine.newBuilder()
                .expireAfterWrite(groovyEngineProperties.getCacheExpireAfterWrite(), TimeUnit.SECONDS)
                .initialCapacity(groovyEngineProperties.getCacheInitialCapacity())
                .maximumSize(groovyEngineProperties.getCacheMaximumSize())
                .build();
    }

    /**
     * 脚本注册中心，依赖于 ScriptLoader ，ScriptLoader实现类由使用方自由选配
     */
    @Bean
    @ConditionalOnMissingBean(ScriptRegistry.class)
    public ScriptRegistry scriptRegistry(ScriptLoader scriptLoader,
                                         @Qualifier("groovyScriptEngineCache") Cache<String, ScriptEntry> cache) {
        return new DefaultScriptRegistry(scriptLoader, cache);
    }

    /**
     * 执行引擎
     *
     * @param scriptRegistry
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(EngineExecutor.class)
    public EngineExecutor engineExecutor(ScriptRegistry scriptRegistry) {
        return new DefaultEngineExecutor(scriptRegistry);
    }

    //todo AutoRefreshScriptExecutor


    @Bean
    @ConditionalOnMissingBean(HotLoadingGroovyScriptAlarm.class)
    public HotLoadingGroovyScriptAlarm hotLoadingGroovyScriptAlarm() {
        return (scriptEntries, ex) -> log.error("script entries load failed,entries:{} exception:{}", scriptEntries, ex);
    }

    @Bean
    public ApplicationContextHelper applicationContextHelper() {
        return new ApplicationContextHelper();
    }

}
