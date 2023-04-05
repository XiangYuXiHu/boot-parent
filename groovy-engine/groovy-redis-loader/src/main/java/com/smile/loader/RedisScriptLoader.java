package com.smile.loader;

import com.smile.config.properties.GroovyRedisLoaderProperties;
import com.smile.groovy.engine.core.compiler.DynamicCodeCompiler;
import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import com.smile.groovy.engine.core.loader.ScriptLoader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 从Redis里加载脚本loader
 *
 * @Description
 * @ClassName RedisScriptLoader
 * @Author smile
 * @date 2023.04.05 19:32
 */
public class RedisScriptLoader implements ScriptLoader {
    private final Logger logger = LoggerFactory.getLogger(RedisScriptLoader.class);

    private final RedisTemplate<String, String> redisTemplate;
    private final DynamicCodeCompiler dynamicCodeCompiler;
    private final GroovyRedisLoaderProperties groovyRedisLoaderProperties;

    public RedisScriptLoader(RedisTemplate<String, String> redisTemplate,
                             DynamicCodeCompiler dynamicCodeCompiler,
                             GroovyRedisLoaderProperties groovyRedisLoaderProperties) {
        this.redisTemplate = redisTemplate;
        this.dynamicCodeCompiler = dynamicCodeCompiler;
        this.groovyRedisLoaderProperties = groovyRedisLoaderProperties;
    }

    @Override
    public ScriptEntry load(@NonNull ScriptQuery query) throws Exception {
        String script = (String) redisTemplate.opsForHash()
                .get(groovyRedisLoaderProperties.getNamespace(), query.getUniqueKey());
        if (!StringUtils.hasText(script)) {
            return null;
        }
        String fingerprint = DigestUtils.md5DigestAsHex(script.getBytes());
        ScriptEntry scriptEntry = new ScriptEntry(query.getUniqueKey(), script, fingerprint, System.currentTimeMillis());
        Class<?> aClass = dynamicCodeCompiler.compile(scriptEntry);
        scriptEntry.setClazz(aClass);
        return scriptEntry;
    }

    @Override
    public List<ScriptEntry> load() throws Exception {
        ArrayList<ScriptEntry> scriptEntries = new ArrayList<>();
        String key = groovyRedisLoaderProperties.getNamespace();
        Set<Object> keys = redisTemplate.opsForHash().keys(key);
        if (CollectionUtils.isEmpty(keys)) {
            logger.error("can not found hashKeys by key [{}].", key);
            return scriptEntries;
        }

        for (Object hashKey : keys) {
            String script = (String) redisTemplate.opsForHash().get(key, hashKey);
            if (!StringUtils.hasText(script)) {
                logger.error("note can not found script content by key [{}] and hashKey [{}]", key, hashKey);
                continue;
            }
            String fingerprint = DigestUtils.md5DigestAsHex(script.getBytes());
            ScriptEntry scriptEntry = new ScriptEntry(hashKey.toString(), script, fingerprint, System.currentTimeMillis());
            scriptEntries.add(scriptEntry);
        }
        return scriptEntries;
    }
}
