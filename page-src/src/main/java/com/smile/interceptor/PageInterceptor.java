package com.smile.interceptor;

import com.smile.entity.Page;
import com.smile.util.ReflectUtil;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @Description
 * @ClassName PageInterceptor
 * @Author smile
 * @date 2023.02.18 21:49
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})})
public class PageInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

    /**
     * 数据库类型
     */
    private String databaseType;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        /**
         * 通过反射获取RoutingStatementHandler的delegate属性
         */
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        /**
         *获取statementhandler的boundSql，handler.gerBoundSql()与delete.getBoundSql()结果一样的，
         *RoutingStatement的实现的所有StatementHandler接口都是调用delegate对应的方法
         */
        BoundSql boundSql = delegate.getBoundSql();
        /**
         * 拿到当前绑定sql的参数对象，就是在调用对应的mapper映射语句时所传的参数
         */
        Object obj = boundSql.getParameterObject();
        /**
         * 传入的是page对象就认定需要进行分页
         */
        if (obj instanceof Page<?>) {
            Page<?> page = (Page<?>) obj;
            /**
             * 通过反射获取delegate父类的BaseStatementHandler的mappedStatement属性
             */
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
            /**
             * 拦截prepare方法参数Connection
             */
            Connection conn = (Connection) invocation.getArgs()[0];
            /**
             * 获取当前需要执行的sql，就是mapper映射中写的sql
             */
            String sql = boundSql.getSql();
            /**
             * 给当前的page参数对象设置总记录数
             */
            setTotalRecord(page, mappedStatement, conn);
            String pageSql = getPageSql(page, sql);
            ReflectUtil.setField(boundSql, "sql", pageSql);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        databaseType = properties.getProperty("databaseType");
    }

    /**
     * 根据page对象获取对应的分页查询sql语句
     *
     * @param page 分页对象
     * @Param sql 原sql
     **/
    private String getPageSql(Page<?> page, String sql) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        if ("mysql".equalsIgnoreCase(databaseType)) {
            return getMysqlPageSql(page, sqlBuilder);
        }
        return sqlBuilder.toString();
    }

    /**
     * 获取数据库的分页查询
     *
     * @param page       分页对象
     * @param sqlBuilder 包含原sql的stringbuffer
     **/
    private String getMysqlPageSql(Page<?> page, StringBuilder sqlBuilder) {
        int offset = (page.getPageStart() - 1) * page.getPageSize();
        sqlBuilder.append(" limit ").append(offset).append(",").append(page.getPageSize());
        return sqlBuilder.toString();
    }

    /**
     * 给当前的对象page设置总记录数
     *
     * @param conn 当前的数据连接
     * @Param page Mapper映射语句对应的参数
     * @Param mappedStatement Mapper映射语句
     **/
    private void setTotalRecord(Page page, MappedStatement mappedStatement, Connection conn) {
        /**
         * 获取对应的boundSql，delegate是利用StatementHandler获取的BoundSql
         */
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        /**
         * 获取在mapper映射中对应的sql
         */
        String sql = boundSql.getSql();
        /**
         * 根据查询sql获取对应的计算总记录数的sql语句
         */
        String countSql = getCountSql(sql);
        /**
         *通过BoundSql获取对应的参数映射
         */
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        /**
         *利用Configuration、查询记录数的sql语句、参数映射关系parameterMapping和参数page对象
         */
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
        DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(countSql);
            parameterHandler.setParameters(preparedStatement);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                page.setTotalSize(totalRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据原sql获取对应的查询总记录数
     *
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        return "select count(1) from ( " + sql + " ) tmp";
    }
}
