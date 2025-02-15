package com.smile.compress;

import java.io.*;

/**
 * @Description
 * @ClassName GenerateData
 * @Author smile
 * @date 2025.01.29 06:58
 */
public class GenerateData {

    public static void main(String[] args) throws IOException {
        String path = "D://words.dat";
        BufferedWriter out = new BufferedWriter(new FileWriter(path));

        int i = 0;
        while (i < 800000) {
            String tempStr = "hello_" + i + " " + "word_" + i;
            out.write(tempStr);
            out.newLine();
            i++;
            if (i % 10000 == 0) {
                out.flush();
            }
        }

        if (out != null) {
            out.close();
        }
        System.out.println("测试文件已生成！");
    }
}
