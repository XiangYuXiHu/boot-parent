package com.smile.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description
 * @ClassName MD5Util
 * @Author smile
 * @date 2023.10.15 10:02
 */
public class MD5Util {

    /**
     * MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。信息摘要是安全的单向哈希函数，它接收任意大小的数据，输出固定长度的哈希值。
     * <p>
     * MessageDigest 对象开始被初始化。该对象通过使用 update 方法处理数据。任何时候都可以调用 reset 方法重置摘要
     * 一旦所有需要更新的数据都已经被更新，应该调用 digest 方法之一完成哈希计算
     * <p>
     * 对于给定数量的更新数据，digest 方法只能被调用一次。digest 被调用后，MessageDigest 对象被重新设置成其初始状态。
     */

    static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static void main(String[] args) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update("a".getBytes());
            System.out.println("md5(a)=" + byte2Str(md5.digest()));
            md5.update("a".getBytes());
            md5.update("bc".getBytes());
            System.out.println("md5(abc)=" + byte2Str(md5.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 其中，>>> 是无符号右移，忽略符号位，空位都以0补齐.
     * 上面两句位移的解释：md5是128位的。一个byte是8个字节。所以一共是16个byte。而md5结果一般是以32个ascii字符出现的。所以每个byte会表示成2个字符。
     * 这就是上面每个byte会处理两次，分别进行hex的原因。
     *
     * @param bytes
     * @return
     */
    private static String byte2Str(byte[] bytes) {
        int len = bytes.length;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            byte b = bytes[i];
            buffer.append(hex[b >>> 4 & 0xf]);
            buffer.append(hex[b & 0xf]);
        }
        return buffer.toString();
    }
}
