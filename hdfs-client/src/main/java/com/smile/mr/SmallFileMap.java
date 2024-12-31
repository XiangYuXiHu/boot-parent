package com.smile.mr;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @ClassName SmallFileMap
 * @Author smile
 * @date 2025.01.01 06:51
 */
public class SmallFileMap {

    public static void main(String[] args) throws IOException {
        write("D:\\data\\smallFile", "/mapFile");

        read("/mapFile");
    }

    private static void write(String inputDir, String outputDir) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.21.110:9000");

        /**
         * 删除hdfs上的输出文件
         */
        FileSystem fileSystem = FileSystem.get(conf);
        fileSystem.delete(new Path(outputDir), true);

        SequenceFile.Writer.Option[] options = {
                MapFile.Writer.keyClass(Text.class),
                MapFile.Writer.valueClass(Text.class)
        };
        MapFile.Writer writer = new MapFile.Writer(conf, new Path(outputDir), options);

        File inputDirPath = new File(inputDir);
        if (inputDirPath.isDirectory()) {
            File[] files = inputDirPath.listFiles();
            for (File file : files) {
                String content = FileUtils.readFileToString(file, "UTF-8");
                String fileName = file.getName();
                Text key = new Text(fileName);
                Text value = new Text(content);
                writer.append(key, value);
            }
        }
        writer.close();
    }

    public static void read(String inputFile) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.21.110:9000");

        MapFile.Reader reader = new MapFile.Reader(new Path(inputFile), conf);
        Text key = new Text();
        Text value = new Text();
        while (reader.next(key, value)) {
            System.out.println("文件名称：" + key.toString() + "," + "文件内容：" + value.toString());
        }
        reader.close();
    }


}
