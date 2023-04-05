package com.smile.groovy.engine.core.helper;

import com.smile.groovy.engine.core.alarm.HotLoadingGroovyScriptAlarm;
import com.smile.groovy.engine.core.compiler.DynamicCodeCompiler;
import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import com.smile.groovy.engine.core.loader.ScriptLoader;
import com.smile.groovy.engine.core.registry.ScriptRegistry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @ClassName RefreshScriptHelper
 * @Author smile
 * @date 2023.04.03 21:49
 */
public class RefreshScriptHelper {

    private Logger logger = LoggerFactory.getLogger(RefreshScriptHelper.class);

    @Autowired
    private ScriptLoader scriptLoader;

    @Autowired
    private ScriptRegistry scriptRegistry;

    @Autowired
    private DynamicCodeCompiler dynamicCodeCompiler;

    @Autowired
    private HotLoadingGroovyScriptAlarm hotLoadingGroovyScriptAlarm;

    /**
     * * <p>
     * * 按条件进行强制刷新脚本
     * *  <ol>
     * *     <li>首先从数据源加载脚本，加载到脚本后编译为Class，然后将脚本注册到本地registry</li>
     * *  </ol>
     * * </p>
     *
     * @param scriptQuery
     * @param allowCover
     * @return
     */
    public boolean refresh(@NonNull ScriptQuery scriptQuery, boolean allowCover) {
        logger.warn("RefreshScriptHelper start refresh groovy script, scriptQuery is : {}", scriptQuery);
        ScriptEntry scriptEntry = null;
        try {
            scriptEntry = scriptLoader.load(scriptQuery);
            if (Objects.isNull(scriptEntry)) {
                logger.warn("can not load script entry by key {}", scriptQuery.getUniqueKey());
                return false;
            }
            ScriptEntry oldScriptEntry = scriptRegistry.findOnCache(scriptQuery);
            if (Objects.isNull(oldScriptEntry) || allowCover ||
                    !oldScriptEntry.getFingerprint().equalsIgnoreCase(scriptEntry.getFingerprint())) {
                Boolean success = scriptRegistry.register(scriptEntry);
                logger.warn("RefreshScriptHelper refresh groovy script result is : [{}], scriptQuery is : {}", success, scriptQuery);
                return success;
            } else {
                logger.warn("can not refresh, oldScriptEntry is : {}, newScriptEntry is : {}", oldScriptEntry, scriptEntry);
                return false;
            }
        } catch (Exception e) {
            hotLoadingGroovyScriptAlarm.alarm(Collections.singletonList(scriptEntry), e);
            return false;
        }
    }

    /**
     * <p>
     * 刷新所有script
     *  <ol>
     *     <li>扫描指定位置的所有脚本，如果脚本信息有变化，则更新本地脚本缓存scriptRegistry</li>
     *     <li>如果脚本没有变化，则不更新缓存，什么也不做</li>
     *  </ol>
     * </p>
     */
    public boolean refreshAll() {
        List<ScriptEntry> scriptEntries = null;
        boolean success = false;
        int addScriptCount = 0;
        int updateScriptCount = 0;
        try {
            logger.info("RefreshScriptHelper start refresh all groovy script.");
            scriptEntries = scriptLoader.load();

            for (ScriptEntry scriptEntry : scriptEntries) {
                ScriptEntry oldScriptEntry = scriptRegistry.findOnCache(new ScriptQuery(scriptEntry.getName()));

                if (Objects.isNull(oldScriptEntry)) {
                    logger.info("can not found script by [{}], register to registry directly.", scriptEntry.getName());
                    Class<?> aClass = dynamicCodeCompiler.compile(scriptEntry);
                    scriptEntry.setClazz(aClass);
                    scriptRegistry.register(scriptEntry);
                    addScriptCount++;
                    continue;
                }

                if (!oldScriptEntry.getFingerprint().equalsIgnoreCase(scriptEntry.getFingerprint())) {
                    logger.info("found script by [{}], but their fingerprints are different, modify it.", scriptEntry.getName());
                    Class<?> aClass = dynamicCodeCompiler.compile(scriptEntry);
                    scriptEntry.setClazz(aClass);
                    scriptEntry.setLastModifiedTime(System.currentTimeMillis());
                    scriptRegistry.register(scriptEntry);
                    updateScriptCount++;
                }
            }
        } catch (Exception e) {
            hotLoadingGroovyScriptAlarm.alarm(scriptEntries, e);
            success = false;
        }
        logger.info("RefreshScriptHelper refresh groovy script end,scriptCount is [{}], addScriptCount is [{}]," +
                " updateScriptCount is [{}]", Objects.isNull(scriptEntries) ?
                null : scriptEntries.size(), addScriptCount, updateScriptCount);
        return success;
    }
}
