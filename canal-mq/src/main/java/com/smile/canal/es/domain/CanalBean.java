package com.smile.canal.es.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @ClassName CanalBean
 * @Author smile
 * @date 2023.03.05 19:34
 */
public class CanalBean implements Serializable {

    /**
     * 变更数据
     */
    private List<Article> data;

    /**
     * 数据源
     */
    private String database;

    /**
     * 源数据库的执行时间
     */
    private long es;

    /**
     * 递增编号
     */
    private int id;

    /**
     * 是否是ddl
     */
    private boolean isDdl;

    /**
     * 字段类型
     */
    private MysqlType mysqlType;

    /**
     * UPDATE语句，旧数据
     */
    private List<Article> old;

    /**
     * 主键名称
     */
    private List<String> pkNames;

    private String sql;

    private SqlType sqlType;

    private String table;
    /**
     * 本次同步数据时间
     */
    private long ts;

    /**
     * (新增)INSERT、(更新)UPDATE、(删除)DELETE、(删除表)ERASE等等
     */
    private String type;

    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public long getEs() {
        return es;
    }

    public void setEs(long es) {
        this.es = es;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDdl() {
        return isDdl;
    }

    public void setDdl(boolean ddl) {
        isDdl = ddl;
    }

    public MysqlType getMysqlType() {
        return mysqlType;
    }

    public void setMysqlType(MysqlType mysqlType) {
        this.mysqlType = mysqlType;
    }

    public List<Article> getOld() {
        return old;
    }

    public void setOld(List<Article> old) {
        this.old = old;
    }

    public List<String> getPkNames() {
        return pkNames;
    }

    public void setPkNames(List<String> pkNames) {
        this.pkNames = pkNames;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CanalBean{" +
                "data=" + data +
                ", database='" + database + '\'' +
                ", es=" + es +
                ", id=" + id +
                ", isDdl=" + isDdl +
                ", mysqlType=" + mysqlType +
                ", old=" + old +
                ", pkNames=" + pkNames +
                ", sql='" + sql + '\'' +
                ", sqlType=" + sqlType +
                ", table='" + table + '\'' +
                ", ts=" + ts +
                ", type='" + type + '\'' +
                '}';
    }
}
