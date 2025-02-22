package com.smile.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Description
 * @ClassName HBaseConn
 * @Author smile
 * @date 2025.02.16 21:51
 */
public class HBaseConn {

    private static Connection connection = null;

    /**
     * 获取链接
     *
     * @return
     * @throws IOException
     */
    public static Connection getConnection() throws IOException {
        if (null == connection || connection.isClosed()) {
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "master:2181,slaveone:2181,slavetwo:2181");
            config.set("hbase.rootdir", "hdfs://master:9000/hbase");
            connection = ConnectionFactory.createConnection(config);
        }
        return connection;
    }

    public static void close() throws IOException {
        if (connection != null) {
            connection.close();
        }
    }
}
