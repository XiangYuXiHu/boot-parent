package com.smile.datasource.core;

import com.smile.datasource.exception.DynamicDataSourceException;
import com.smile.datasource.provider.DynamicDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 核心动态数据源组件
 *
 * @Description
 * @ClassName DynamicRoutingDataSource
 * @Author smile
 * @date 2023.04.15 15:23
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private Logger log = LoggerFactory.getLogger(DynamicRoutingDataSource.class);
    protected DynamicDataSourceProvider provider;
    protected String primary;
    private Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();


    @Override
    protected DataSource determineDataSource() {
        DataSource dataSource = dataSourceMap.get(DynamicDataSourceContextHolder.getDatasourceLookupKey());
        return dataSource == null ? determinePrimaryDataSource() : dataSource;
    }

    /**
     * 获取默认数据源
     *
     * @return
     */
    public DataSource determinePrimaryDataSource() {
        return dataSourceMap.get(primary);
    }

    public synchronized void addDataSource(String ds, DataSource dataSource) {
        dataSourceMap.put(ds, dataSource);
        log.info("动态数据源-加载 {} 成功", ds);
    }

    /**
     * 删除数据源
     *
     * @param ds 数据源名称
     */
    public synchronized void removeDataSource(String ds) {
        dataSourceMap.remove(ds);
        log.info("动态数据源 {} 删除成功.", ds);
    }

    public void init() {
        Map<String, DataSource> dataSourceMap = provider.loadDataSources();
        for (Map.Entry<String, DataSource> ds : dataSourceMap.entrySet()) {
            addDataSource(ds.getKey(), ds.getValue());
        }
        //检测默认数据源设置
        if (dataSourceMap.containsKey(primary)) {
            log.info("当前的默认数据源是单数据源，数据源名为 {}", primary);
        } else {
            throw new DynamicDataSourceException("请检查primary默认数据库设置");
        }
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public DynamicDataSourceProvider getProvider() {
        return provider;
    }

    public void setProvider(DynamicDataSourceProvider provider) {
        this.provider = provider;
    }
}
