package com.smile.groovy.engine.core.registry;

import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;

import java.util.List;
import java.util.Map;

/**
 * 引擎注册中心
 *
 * @author smile
 */
public interface ScriptRegistry {

    /**
     * 注册脚本，如果脚本项存在则强制覆盖
     *
     * @param scriptEntry
     * @return
     */
    Boolean register(ScriptEntry scriptEntry);

    /**
     * 注册脚本，如果脚本项存在是否覆盖，则取决于 allowToCover
     *
     * @param scriptEntry
     * @param allowToCover
     * @return
     */
    Boolean register(ScriptEntry scriptEntry, boolean allowToCover);

    /**
     * 批量注册脚本项，如果脚本项存在则强制覆盖
     *
     * @param scriptEntry
     * @return
     */
    Boolean batchRegister(List<ScriptEntry> scriptEntry);

    /**
     * 批量注册脚本项，如果脚本项存在是否覆盖，则取决于 allowToCover
     *
     * @param scriptEntry
     * @param allowToCover
     * @return
     */
    Boolean batchRegister(List<ScriptEntry> scriptEntry, boolean allowToCover);

    /**
     * 只从缓存中查找脚本，如果缓存中没有，则返回空（不从数据源进行加载）
     *
     * @param scriptQuery
     * @return
     */
    ScriptEntry findOnCache(ScriptQuery scriptQuery);

    /**
     * 条件查询脚本项，如果缓存中不存在，则从数据源按条件进行加载，加载到后放入缓存并返回
     *
     * @param scriptQuery
     * @return
     * @throws Exception
     */
    ScriptEntry find(ScriptQuery scriptQuery) throws Exception;

    /**
     * * <p>
     * * 从缓存中获取所有脚本项
     * *  <ol>
     * *     参数 {@code needLatestData} 表示：是否需要最新数据
     * *     <li>如果为true，则会先去数据源拉取一遍数据并写入到缓存，然后返回</li>
     * *     <li>如果为false则直接返回当前缓存里的数据</li>
     * *  </ol>
     * * </p>
     *
     * @param isLatest
     * @return
     */
    Map<String, ScriptEntry> findAllOnCache(boolean isLatest);

    /**
     * 清除注册中心缓存
     */
    void clear();

    /**
     * 按条件清除
     *
     * @param scriptQuery
     * @return
     */
    void clear(ScriptQuery scriptQuery);
}
