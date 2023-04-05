package com.smile.helper;

import com.smile.config.properties.GroovyRedisLoaderProperties;
import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import com.smile.groovy.engine.core.helper.RegisterScriptHelper;
import com.smile.groovy.engine.core.loader.ScriptLoader;
import com.smile.groovy.engine.core.registry.ScriptRegistry;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @ClassName ManualRegisterScriptHelper
 * @Author smile
 * @date 2023.04.05 20:06
 */

public class ManualRegisterScriptHelper implements RegisterScriptHelper {

    private Logger logger = LoggerFactory.getLogger(ManualRegisterScriptHelper.class);

    private ScriptLoader scriptLoader;
    private ScriptRegistry scriptRegistry;
    private RedisTemplate<String, String> redisTemplate;
    private GroovyRedisLoaderProperties groovyRedisLoaderProperties;

    public ManualRegisterScriptHelper(ScriptLoader scriptLoader, ScriptRegistry scriptRegistry,
                                      RedisTemplate<String, String> redisTemplate,
                                      GroovyRedisLoaderProperties groovyRedisLoaderProperties) {
        this.scriptLoader = scriptLoader;
        this.scriptRegistry = scriptRegistry;
        this.redisTemplate = redisTemplate;
        this.groovyRedisLoaderProperties = groovyRedisLoaderProperties;
    }

    @Override
    public boolean registerScript(@NonNull String name, @NonNull String content, boolean allowCover) throws Exception {
        logger.warn("start manual register script, name is : [{}], script content is : {}", name, content);
        if (StringUtils.isBlank(name) || StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("name and content can not be null.");
        }
        boolean success = false;
        Object oldScript = redisTemplate.opsForHash().get(groovyRedisLoaderProperties.getNamespace(), name);
        if (Objects.isNull(oldScript) || allowCover) {
            redisTemplate.opsForHash().put(groovyRedisLoaderProperties.getNamespace(), name, content);
            logger.warn("[{}] script store to redis successfully.", name);
            ScriptEntry scriptEntry = scriptLoader.load(new ScriptQuery(name));
            success = scriptRegistry.register(scriptEntry);
            logger.warn("[{}] script register to registry result is : [{}].", name, success);
        }
        return success;
    }

    @Override
    public boolean batchRegisterScript(Map<String, String> scriptMap, boolean allowCover) throws Exception {
        if (null == scriptMap) {
            return false;
        }
        logger.warn("batch register script start.");
        scriptMap.forEach((key, content) -> {
            try {
                registerScript(key, content, allowCover);
            } catch (Exception e) {
                throw new RuntimeException("register failed，please retry.", e);
            }
        });
        logger.warn("batch register script success.");
        return true;
    }
}
