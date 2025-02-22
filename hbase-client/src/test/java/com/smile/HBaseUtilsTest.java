package com.smile;

import com.smile.hbase.HBaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试
 *
 * @author zhangzhicheng zhangzhicheng@tuhu.cn
 * @create 2019/12/31 11:04
 * @since JDK8
 */
@Slf4j
public class HBaseUtilsTest {

    private static final String TABLE_NAME = "class";
    private static final String TEACHER = "teacher";
    private static final String STUDENT = "student";
    private static final String ROWKEY = "row";

    @Test
    public void createTable() {
        List<String> familyColumns = Arrays.asList(TEACHER, STUDENT);
        HBaseUtil.createTable(TABLE_NAME, familyColumns);
    }

    @Test
    public void putTest() {
        for (int i = 0; i < 10; i++) {
            HBaseUtil.put(TABLE_NAME, ROWKEY + i, TEACHER, "name", "Lucy" + i);
            HBaseUtil.put(TABLE_NAME, ROWKEY + i, TEACHER, "age", "4" + i);
            HBaseUtil.put(TABLE_NAME, ROWKEY + i, STUDENT, "name", "mimi" + i);
            HBaseUtil.put(TABLE_NAME, ROWKEY + i, STUDENT, "age", "2" + i);
//            HBaseUtil.put(TABLE_NAME, ROWKEY + i, "companyInfo", "address", "usa" + i);
        }
    }

    @Test
    public void getTest() {
        Result result = HBaseUtil.get(TABLE_NAME, ROWKEY + "0");
        if (result != null) {
            log.info("取得的值:{}", Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
            log.info("取得的值:{}", Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
        }
    }

    @Test
    public void getCellTest() {
        String name = HBaseUtil.get(TABLE_NAME, ROWKEY + "0", TEACHER, "name");
        log.info("取出名字:{}", name);
    }

    @Test
    public void scannerTest() {
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{}   {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
            });
            scanner.close();
        }
    }

    @Test
    public void scannerWithFilterTest() {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(TEACHER), Bytes.toBytes("name"), CompareOperator.EQUAL, Bytes.toBytes("Lucy0"));
        filterList.addFilter(singleColumnValueFilter);
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, filterList);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
            });
            scanner.close();
        }
    }

    @Test
    public void scannerwithRangeTest() {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(TEACHER), Bytes.toBytes("age"), CompareOperator.EQUAL, Bytes.toBytes("48"));
        filterList.addFilter(singleColumnValueFilter);
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, "row0", "row9", filterList);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("age"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
            });
            scanner.close();
        }
    }

    @Test
    public void deleteColumnTest() {
        HBaseUtil.deleteColumn(TABLE_NAME, "row0", TEACHER, "name");
    }

    @Test
    public void deleteRowTest() {
        HBaseUtil.deleteRow(TABLE_NAME, "row0");
    }

    @Test
    public void deleteTable() {
        HBaseUtil.deleteTable(TABLE_NAME);
    }

    @Test
    public void addColumnFamilyTest() {
        HBaseUtil.addColumnFamily(TABLE_NAME, "companyInfo");
    }

    @Test
    public void deleteColumnFamilyTest() {
        HBaseUtil.deleteColumnFamily(TABLE_NAME, "companyInfo");
    }

    @Test
    public void getScannerTest() {
        RowFilter rowFilter = new RowFilter(CompareOperator.GREATER_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row0")));
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, rowFilter);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
            });
        }
    }

    /**
     * 首先会去查找 teacher:name 中值以 Lu 开头的所有数据获得 参考数据集，这一步等同于 valueFilter 过滤器；
     * <p>
     * 其次再用参考数据集中所有数据的时间戳去检索其他列，获得时间戳相同的其他列的数据作为 结果数据集，这一步等同于时间戳过滤器；
     * <p>
     * 最后如果 dropDependentColumn 为 true，则返回 参考数据集+结果数据集，若为 false，则抛弃参考数据集，只返回 结果数据集
     */
    @Test
    public void getScannerAgainTest() {
        DependentColumnFilter dependentColumnFilter = new DependentColumnFilter(
                Bytes.toBytes(TEACHER), Bytes.toBytes("name"), true, CompareOperator.EQUAL, new BinaryPrefixComparator(Bytes.toBytes("Lu")));
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, dependentColumnFilter);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
            });
        }
    }

    @Test
    public void getSingleColumnValueFilterTest() {
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(
                Bytes.toBytes(STUDENT), Bytes.toBytes("name"), CompareOperator.EQUAL, Bytes.toBytes("mimi6")
        );
        singleColumnValueFilter.setFilterIfMissing(true);
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, singleColumnValueFilter);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("age"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("age"))));
            });
            scanner.close();
        }
    }

    @Test
    public void getPrefixFilterTest() {
        PrefixFilter prefixFilter = new PrefixFilter(Bytes.toBytes("row1"));
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, prefixFilter);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("age"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("age"))));
            });
            scanner.close();
        }
    }

    @Test
    public void columnPrefixFilterTest() {
        ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(Bytes.toBytes("name"));
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, columnPrefixFilter);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("age"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("age"))));
            });
            scanner.close();
        }
    }


    @Test
    public void skipFilterTest() {
        ValueFilter valueFilter = new ValueFilter(CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("23")));
        SkipFilter skipFilter = new SkipFilter(valueFilter);
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, skipFilter);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("age"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("age"))));
            });
            scanner.close();
        }
    }

    @Test
    public void timestampsFilterTest() {
        List<Long> list = new ArrayList<>();
        list.add(1740179035L);
        TimestampsFilter timestampsFilter = new TimestampsFilter(list);
        ResultScanner scanner = HBaseUtil.getScanner(TABLE_NAME, timestampsFilter);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("age"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("age"))));
            });
            scanner.close();
        }
    }

    @Test
    public void testQueryDataPage() {
        ResultScanner scanner = HBaseUtil.queryDataPage(TABLE_NAME, 1, 2);
        if (scanner != null) {
            scanner.forEach(result -> {
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(TEACHER), Bytes.toBytes("age"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
                log.info("{} {}", Bytes.toString(result.getRow()), Bytes.toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("age"))));
            });
            scanner.close();
        }
    }


}
