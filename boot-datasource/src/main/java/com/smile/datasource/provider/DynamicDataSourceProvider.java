package com.smile.datasource.provider;

import javax.sql.DataSource;
import java.util.Map;

/**
 * * 多数据源加载接口，默认的实现为从yml信息中加载所有数据源
 *
 * @Description
 * @ClassName DynamicDataSourceProvider
 * @Author smile
 * @date 2023.04.15 15:24
 */
public interface DynamicDataSourceProvider {

    /**
     * 加载所有数据源
     *
     * @return 所有数据源，key为数据源名称
     */
    Map<String, DataSource> loadDataSources();
}
