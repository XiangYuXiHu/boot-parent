package com.smile.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description
 * @ClassName DesUtil
 * @Author smile
 * @date 2023.10.15 09:59
 */
public class DesUtil {

    /**
     * 使用DES对字符串加密
     *
     * @param str str utf8编码的字符串
     * @param key key 密钥（56位，7字节）
     * @return
     */
    public static byte[] desEncrypt(String str, String key) throws Exception {
        if (null == str || null == key) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return bytes;
    }

    /**
     * 使用DES对数据解密
     *
     * @param bytes utf8编码的二进制数据
     * @param key   密钥（16字节）
     * @return
     */
    public static String desDecrypt(byte[] bytes, String key) throws Exception {
        if (null == bytes || null == key) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
}
