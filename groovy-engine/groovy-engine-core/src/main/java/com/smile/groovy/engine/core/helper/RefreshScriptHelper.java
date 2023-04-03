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


}
