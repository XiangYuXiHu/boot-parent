package com.smile.datasource.provider;

import com.smile.datasource.config.properties.DataSourceProperty;
import com.smile.datasource.config.properties.DynamicDataSourceProperties;
import com.smile.datasource.creator.DynamicDataSourceCreator;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName YmlDynamicDataSourceProvider
 * @Author smile
 * @date 2023.04.15 15:25
 */
public class YmlDynamicDataSourceProvider implements DynamicDataSourceProvider {

    /**
     * 多数据源
     */
    private DynamicDataSourceProperties properties;
    /**
     * 多数据源创建器
     */
    private DynamicDataSourceCreator dynamicDataSourceCreator;

    public YmlDynamicDataSourceProvider(DynamicDataSourceProperties properties, DynamicDataSourceCreator dynamicDataSourceCreator) {
        this.properties = properties;
        this.dynamicDataSourceCreator = dynamicDataSourceCreator;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> datasourcePropertiesMap = properties.getDatasource();
        if (datasourcePropertiesMap.size() < 1) {
            throw new RuntimeException("error please check datasource config, or cancel @EnableDynamicDataSource.");
        }
        Map<String, DataSource> dataSourceMap = new HashMap<>(datasourcePropertiesMap.size());
        for (Map.Entry<String, DataSourceProperty> item : datasourcePropertiesMap.entrySet()) {
            String poolName = item.getKey();
            DataSourceProperty dataSourceProperty = item.getValue();
            dataSourceProperty.setPollName(poolName);
            dataSourceMap.put(poolName, dynamicDataSourceCreator.createDataSource(dataSourceProperty));
        }
        return dataSourceMap;
    }
}
