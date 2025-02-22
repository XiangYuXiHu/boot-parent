package com.smile.hbase;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.protobuf.generated.FilterProtos;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.smile.hbase.HBaseConn.getConnection;

/**
 * @Description
 * @ClassName HBaseUtil
 * @Author smile
 * @date 2025.02.18 21:32
 */
@Slf4j
public class HBaseUtil {


    /**
     * 创建表
     *
     * @param tableName
     * @param columnFamilies
     */
    public static boolean createTable(String tableName, List<String> columnFamilies) {
        Admin admin = null;
        try {
            Connection connection = getConnection();
            admin = connection.getAdmin();
            TableName table = TableName.valueOf(tableName);
            if (admin.tableExists(table)) {
                log.error("表名:{}已经存在！", tableName);
                return false;
            }
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(table);
            columnFamilies.stream().forEach(cf -> {
                ColumnFamilyDescriptorBuilder cfDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf));
                ColumnFamilyDescriptor familyDescriptor = cfDescriptor.build();
                tableDescriptorBuilder.setColumnFamily(familyDescriptor);
            });
            admin.createTable(tableDescriptorBuilder.build());
        } catch (IOException e) {
            log.error("创建表异常:{}", e.getMessage());
            return false;
        } finally {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 添加列簇
     *
     * @param tableName
     * @param columnFamily
     */
    public static void addColumnFamily(String tableName, String columnFamily) {
        Admin admin = null;
        try {
            Connection connection = getConnection();
            admin = connection.getAdmin();

            TableName table = TableName.valueOf(tableName);
            if (!admin.tableExists(table)) {
                log.error("表名:{}不存在！", tableName);
                return;
            }

            ColumnFamilyDescriptor familyDescriptor = ColumnFamilyDescriptorBuilder.of(columnFamily);
            admin.addColumnFamily(table, familyDescriptor);
        } catch (IOException e) {
            log.error("添加列族异常:{}", e.getMessage());
        } finally {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除列簇
     *
     * @param tableName
     * @param columnFamily
     */
    public static void deleteColumnFamily(String tableName, String columnFamily) {
        Admin admin = null;
        try {
            Connection connection = getConnection();
            admin = connection.getAdmin();

            TableName table = TableName.valueOf(tableName);
            if (!admin.tableExists(table)) {
                log.error("表名:{}不存在！", tableName);
                return;
            }

            admin.deleteColumnFamily(table, Bytes.toBytes(columnFamily));
        } catch (IOException e) {
            log.error("删除列族异常:{}", e.getMessage());
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除表
     *
     * @param tableName
     */
    public static void deleteTable(String tableName) {
        Admin admin = null;
        try {
            Connection connection = getConnection();
            admin = connection.getAdmin();

            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 插入行数据
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param qualifier
     * @param value
     */
    public static void put(String tableName, String rowKey, String columnFamily, String qualifier, String value) {
        try (Connection connection = getConnection()) {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            table.close();
        } catch (IOException e) {
            log.error("表中插入数据异常:{}", e.getMessage());
        }
    }

    /**
     * 根据rowKey获取数据
     *
     * @param tableName
     * @param rowKey
     * @return
     */
    public static Result get(String tableName, String rowKey) {
        try (Connection connection = getConnection()) {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            return table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取表中的数据
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @return
     */
    public static Result get(String tableName, String rowKey, String columnFamily) {
        try (Connection connection = getConnection()) {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addFamily(Bytes.toBytes(columnFamily));
            return table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取数据
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param qualifier
     * @return
     */
    public static String get(String tableName, String rowKey, String columnFamily, String qualifier) {
        try (Connection connection = getConnection()) {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
            Result result = table.get(get);
            byte[] resultValue = result.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
            return new String(resultValue);
        } catch (IOException e) {
            log.error("获取数据异常:{}", e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    /**
     * 检索全表
     *
     * @param tableName
     * @return
     */
    public static ResultScanner getScanner(String tableName) {
        try {
            Connection connection = getConnection();
            Table table = connection.getTable(TableName.valueOf((tableName)));
            Scan scan = new Scan();
            return table.getScanner(scan);
        } catch (IOException e) {
            log.error("全表检索异常:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 检索指定数据
     *
     * @param tableName
     * @param filterList
     * @return
     */
    public static ResultScanner getScanner(String tableName, FilterList filterList) {
        try {
            Connection connection = getConnection();
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.setFilter(filterList);
            return table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param tableName
     * @param filter
     * @return
     */
    public static ResultScanner getScanner(String tableName, Filter filter) {
        try {
            Connection connection = getConnection();
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.setFilter(filter);
            return table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检索表中指定数据
     *
     * @param tableName
     * @param startRowKey
     * @param endRowKey
     * @param filterList
     * @return
     */
    public static ResultScanner getScanner(String tableName, String startRowKey, String endRowKey, FilterList filterList) {
        try {
            Connection connection = getConnection();
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.withStartRow(Bytes.toBytes(startRowKey));
            scan.withStopRow(Bytes.toBytes(endRowKey));
            scan.setFilter(filterList);
            return table.getScanner(scan);
        } catch (IOException e) {
            log.error("检索表中指定数据:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 删除指定的行
     *
     * @param tableName
     * @param rowKey
     */
    public static void deleteRow(String tableName, String rowKey) {
        try (Connection connection = getConnection()) {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } catch (IOException e) {
            log.error("删除指定的行异常:{}", e.getMessage());
        }
    }

    /**
     * 删除指定的列
     *
     * @param tableName
     * @param rowKey
     * @param familyName
     * @param qualifier
     */
    public static void deleteColumn(String tableName, String rowKey, String familyName, String qualifier) {
        Table table;
        try (Connection connection = HBaseConn.getConnection()) {
            table = connection.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumns(Bytes.toBytes(familyName), Bytes.toBytes(qualifier));
            table.delete(delete);
            table.close();
        } catch (IOException e) {
            log.error("删除指定的行异常:{}", e.getMessage());
        }
    }

    /**
     * 分页查询
     *
     * @param tableName
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static ResultScanner queryDataPage(String tableName, int currentPage, int pageSize) {
        String startRowKey = null;
        ResultScanner results = null;
        for (int i = 0; i < currentPage; i++) {
            results = queryDate(tableName, startRowKey, pageSize);
            if (i < currentPage - 1) {
                Iterator<Result> iterator = results.iterator();
                while (iterator.hasNext()) {
                    startRowKey = Bytes.toString(iterator.next().getRow());
                }
            }
        }
        return results;
    }

    public static ResultScanner queryDate(String tableName, String startRowKey, int pageSize) {
        ResultScanner results = null;
        Scan scan = new Scan();
//        scan.setReversed(true);
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(new PageFilter(pageSize));

        if (StringUtils.isNoneBlank(startRowKey)) {
            RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.GREATER,
                    new BinaryComparator(Bytes.toBytes(startRowKey)));
            filterList.addFilter(rowFilter);
        }
        scan.setFilter(filterList);

        try {
            Connection connection = getConnection();
            Table table = connection.getTable(TableName.valueOf(tableName));
            results = table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
