package com.smile;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.eclipse.jetty.server.MultiParts;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * Unit test for simple App.
 */
public class HdfsTest {

    private URI uri;
    private Configuration conf;
    private String user;
    private FileSystem fileSystem;

    @Before
    public void init() throws IOException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.21.110:9000");
        fileSystem = FileSystem.get(URI.create("hdfs://192.168.21.110:9000"), conf, "root");
    }

    @Test
    public void listFile() throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/usr/hadoop"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus file = listFiles.next();
            System.out.println("文件路径：" + file.getPath() + ",文件权限：" + file.getPermission());
            System.out.println("文件主人：" + file.getOwner() + ",文件权限：" + file.getLen());
        }
    }

    @Test
    public void testAddFile2Hdfs() throws IOException {
        Path src = new Path("D:\\data\\emp.txt");
        Path dst = new Path("/usr/hadoop/local");
        fileSystem.copyFromLocalFile(src, dst);
        fileSystem.close();
    }

    @Test
    public void testDownloadFileToLocal() throws IOException {
        fileSystem.copyToLocalFile(new Path("/usr/hadoop/local/emp.txt"), new Path("d:\\"));
    }

    @Test
    public void testMkdirAndDeleteAndRename() throws IOException {
        fileSystem.mkdirs(new Path("/user/local"));
        fileSystem.rename(new Path("/user"), new Path("/usr"));
        fileSystem.mkdirs(new Path("/user/locals"));
        fileSystem.delete(new Path("/user/locals"), true);
    }
}
