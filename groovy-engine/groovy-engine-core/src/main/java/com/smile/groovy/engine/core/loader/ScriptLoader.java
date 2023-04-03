package com.smile.groovy.engine.core.loader;

import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 脚本加载器
 *
 * @author
 */
public interface ScriptLoader {

    /**
     * 加载脚本，如果缓存中不存在，则从数据源查找，找到后将脚本编译为Class
     *
     * @param query
     * @return
     * @throws Exception
     */
    ScriptEntry load(@NonNull ScriptQuery query) throws Exception;

    /**
     * 从数据源预加载所有的脚本（不会将脚本编译为Class）
     *
     * @return
     * @throws Exception
     */
    List<ScriptEntry> load() throws Exception;
}
