package com.smile.config;

import com.smile.config.properties.GroovyRedisLoaderProperties;
import com.smile.groovy.engine.core.compiler.DynamicCodeCompiler;
import com.smile.groovy.engine.core.loader.ScriptLoader;
import com.smile.groovy.engine.core.registry.ScriptRegistry;
import com.smile.helper.ManualRegisterScriptHelper;
import com.smile.loader.RedisScriptLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description
 * @ClassName GroovyRedisLoaderAutoConfiguration
 * @Author smile
 * @date 2023.04.05 19:27
 */
@Configuration
@EnableConfigurationProperties(value = {GroovyRedisLoaderProperties.class})
@ConditionalOnProperty(prefix = GroovyRedisLoaderProperties.PREFIX, value = "enable", havingValue = "true")
public class GroovyRedisLoaderAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(GroovyRedisLoaderAutoConfiguration.class);

    /**
     * * 注册基于Redis的ScriptLoader，配置里必须要显示开启该加载器时才注入 {@code groovy.engine.redis-loader.enable}
     * * 需要依赖于RedisTemplate，所以项目里必须要配置redis
     *
     * @param redisTemplate
     * @param dynamicCodeCompiler
     * @param groovyRedisLoaderProperties
     * @return
     */
    @Bean
    public ScriptLoader scriptLoader(RedisTemplate<String, String> redisTemplate,
                                     DynamicCodeCompiler dynamicCodeCompiler,
                                     GroovyRedisLoaderProperties groovyRedisLoaderProperties) {
        logger.info("loading ScriptLoader type is [{}]", RedisScriptLoader.class);
        return new RedisScriptLoader(redisTemplate, dynamicCodeCompiler, groovyRedisLoaderProperties);
    }

    /**
     * 注入手动注册脚本助手
     *
     * @param scriptRegistry
     * @param scriptLoader
     * @param redisTemplate
     * @param groovyRedisLoaderProperties
     * @return
     */
    @Bean
    public ManualRegisterScriptHelper manualRegisterScriptHelper(ScriptRegistry scriptRegistry,
                                                                 ScriptLoader scriptLoader,
                                                                 RedisTemplate<String, String> redisTemplate,
                                                                 GroovyRedisLoaderProperties groovyRedisLoaderProperties) {
        return new ManualRegisterScriptHelper(scriptLoader, scriptRegistry, redisTemplate, groovyRedisLoaderProperties);
    }
}
