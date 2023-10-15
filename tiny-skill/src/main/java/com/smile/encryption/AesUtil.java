package com.smile.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密算法AES
 *
 * @Description
 * @ClassName AesUtil
 * @Author smile
 * @date 2023.10.15 09:58
 */
public class AesUtil {

    /**
     * 使用AES对字符串加密
     *
     * @param str str utf8编码的字符串
     * @param key key 密钥（16字节）
     * @return
     */
    public static byte[] aesEncrypt(String str, String key) throws Exception {
        if (null == str || null == key) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        return cipher.doFinal(str.getBytes("utf-8"));
    }

    public static String aesDecrypt(byte[] bytes, String key) throws Exception {
        if (null == bytes || null == key) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
}
