package com.smile.groovy.engine.core.alarm;

import com.smile.groovy.engine.core.domain.ScriptEntry;

import java.util.List;

/**
 * 热加载脚本告警接口
 *
 * @author smile
 */
public interface HotLoadingGroovyScriptAlarm {

    /**
     * 加载脚本异常告警
     *
     * @param scriptEntries
     * @param ex
     */
    void alarm(List<ScriptEntry> scriptEntries, Throwable ex);
}
