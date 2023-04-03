package com.smile.groovy.engine.core.registry;

import com.github.benmanes.caffeine.cache.Cache;
import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import com.smile.groovy.engine.core.exception.LoadScriptException;
import com.smile.groovy.engine.core.exception.RegisterScriptException;
import com.smile.groovy.engine.core.loader.ScriptLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 默认注册中心
 *
 * @Description
 * @ClassName DefaultScriptRegistry
 * @Author smile
 * @date 2023.04.02 18:41
 */
public class DefaultScriptRegistry implements ScriptRegistry {
    private Logger logger = LoggerFactory.getLogger(DefaultScriptRegistry.class);

    private final Cache<String, ScriptEntry> cache;
    private ScriptLoader scriptLoader;

    public DefaultScriptRegistry(ScriptLoader scriptLoader, Cache<String, ScriptEntry> cache) {
        this.scriptLoader = scriptLoader;
        this.cache = cache;
    }

    @Override
    public Boolean register(ScriptEntry scriptEntry) {
        if (Objects.isNull(scriptEntry)) {
            logger.warn("script Entry is null,defaultScriptRegistry#register failed");
            return false;
        }
        cache.put(scriptEntry.getName(), scriptEntry);
        return true;
    }

    @Override
    public Boolean register(ScriptEntry scriptEntry, boolean allowToCover) {
        ScriptEntry oldEntry = cache.getIfPresent(scriptEntry.getName());
        if (Objects.isNull(oldEntry) || allowToCover) {
            return register(scriptEntry);
        }
        logger.info("script entry {} is already exist and not allow to cover", scriptEntry.getName());
        return false;
    }

    @Override
    public Boolean batchRegister(List<ScriptEntry> scriptEntries) {
        return batchRegister(scriptEntries, true);
    }

    @Override
    public Boolean batchRegister(List<ScriptEntry> scriptEntries, boolean allowToCover) {
        if (CollectionUtils.isEmpty(scriptEntries)) {
            logger.warn("scriptEntry is not empty,not register");
            return false;
        }
        boolean result = false;
        for (ScriptEntry scriptEntry : scriptEntries) {
            result &= register(scriptEntry, allowToCover);
        }
        return result;
    }

    @Override
    public ScriptEntry findOnCache(ScriptQuery scriptQuery) {
        return cache.getIfPresent(scriptQuery.getUniqueKey());
    }

    @Override
    public ScriptEntry find(ScriptQuery scriptQuery) throws Exception {
        ScriptEntry entry = cache.getIfPresent(scriptQuery.getUniqueKey());
        if (Objects.nonNull(entry)) {
            return entry;
        }
        synchronized (scriptQuery.getUniqueKey().intern()) {
            entry = cache.getIfPresent(scriptQuery.getUniqueKey());
            if (Objects.isNull(entry)) {
                entry = scriptLoader.load(scriptQuery);
                if (Objects.isNull(entry)) {
                    String message = String.format("load Script Entry fail,uniqueKey{}", scriptQuery.getUniqueKey());
                    throw new LoadScriptException(message);
                }
                entry.setName(scriptQuery.getUniqueKey());
                register(entry);
            }
        }
        return entry;
    }

    @Override
    public Map<String, ScriptEntry> findAllOnCache(boolean isLatest) {
        if (isLatest) {
            List<ScriptEntry> scriptEntries;
            try {
                scriptEntries = scriptLoader.load();
            } catch (Exception e) {
                throw new LoadScriptException("load all script entries failed", e);
            }
            if (!batchRegister(scriptEntries)) {
                /**
                 * 注册失败，抛出异常，保证获取到的数据一定是最新的
                 */
                throw new RegisterScriptException("register all script entries failed");
            }
        }
        return cache.asMap();
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public void clear(ScriptQuery scriptQuery) {
        cache.invalidate(scriptQuery.getUniqueKey());
    }
}
