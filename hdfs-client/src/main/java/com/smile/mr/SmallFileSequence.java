package com.smile.mr;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.File;
import java.io.IOException;

/**
 * 小文件合并
 *
 * @Description
 * @ClassName SmallFileSequence
 * @Author smile
 * @date 2024.12.29 21:34
 */
public class SmallFileSequence {

    public static void main(String[] args) throws IOException {
        write("D:\\data\\smallFile", "/seqFile");

        read("/seqFile");
    }

    private static void write(String inputDir, String outputFile) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.21.110:9000");

        /**
         * 删除hdfs上的输出文件
         */
        FileSystem fileSystem = FileSystem.get(conf);
        fileSystem.delete(new Path(outputFile), true);

        SequenceFile.Writer.Option[] options = {
                SequenceFile.Writer.file(new Path(outputFile)),
                SequenceFile.Writer.keyClass(Text.class),
                SequenceFile.Writer.valueClass(Text.class)
        };
        SequenceFile.Writer writer = SequenceFile.createWriter(conf, options);

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

        SequenceFile.Reader reader = new SequenceFile.Reader(conf, SequenceFile.Reader.file(new Path(inputFile)));
        Text key = new Text();
        Text value = new Text();
        while (reader.next(key, value)) {
            System.out.println("文件名称：" + key.toString() + "," + "文件内容：" + value.toString());
        }
        reader.close();
    }
}
