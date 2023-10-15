package com.smile.encryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName RsaUtil
 * @Author smile
 * @date 2023.10.15 10:04
 */
public class RsaUtil {


    /**
     * 加密算法RSA
     */
    private static String ALGORITHM = "RSA";

    /**
     * 指定加密模式与填充方式
     */
    private static String ALGORITHM_MODEL = "RSA/ECB/PKCS1Padding";

    /**
     * 指定key的大小，一般为1024，越大安全性越高
     */
    private static int KEYSIZE = 1024;

    /**
     * 公钥存放文件
     */
    private static String PUBLIC_FILE = "PublicKey";

    /**
     * 私钥存放文件
     */
    private static String PRIVATE_FILE = "PrivateKey";

    /**
     * 生成密钥对
     */
    private static void generateKeyPair() throws Exception {
        /**
         *RSA算法要求有一个可信任的随机数源
         */
        SecureRandom secureRandom = new SecureRandom();
        /**
         * 为RSA算法创建一个keyPairGenerator
         */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        /**
         *利用上面的随机数据源初始化这个KeyPairGenerator对象
         */
        keyPairGenerator.initialize(KEYSIZE, secureRandom);
        /**
         * 生成密钥对
         */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        /**
         * 公钥
         */
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PUBLIC_FILE));
        oos.writeObject(publicKey);
        oos.close();
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(PRIVATE_FILE));
        outputStream.writeObject(privateKey);
        outputStream.close();
    }

    /**
     * Base64编码
     *
     * @return
     */
    public static Map<String, String> genKeyPair() throws Exception {
        Map<String, String> ret = new HashMap<>();
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEYSIZE, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        byte[] pb = publicKey.getEncoded();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String pbStr = base64Encoder.encodeBuffer(pb);
        ret.put("publicKey", pbStr);

        byte[] pr = privateKey.getEncoded();
        String prStr = base64Encoder.encodeBuffer(pr);
        ret.put("privateKey", prStr);
        System.out.println("====" + publicKey.toString() + "---" + pr);
        return ret;
    }

    /**
     * 从base64编码的公钥恢复公钥
     *
     * @param pbBase64
     * @return
     */
    public static PublicKey getPublicKey(String pbBase64) throws Exception {
        byte[] pb = (new BASE64Decoder()).decodeBuffer(pbBase64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pb);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从base64编码的私钥恢复私钥
     *
     * @param prBase64
     * @return
     */
    public PrivateKey getPrivateKey(String prBase64) throws Exception {
        byte[] pr = (new BASE64Decoder()).decodeBuffer(prBase64);
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(pr);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(encodedKeySpec);
    }

    /**
     * 加密方法 source源数据
     *
     * @param source
     * @return
     */
    public static byte[] encrypt(String source) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_FILE));
        Key key = (Key) ois.readObject();
        ois.close();
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODEL);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = source.getBytes();
        return cipher.doFinal(bytes);
    }

    /**
     * 解密算法
     *
     * @param cryptograph 密文
     * @return
     */
    public static String decrypt(byte[] cryptograph) throws Exception {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_FILE));
        Key key = (Key) inputStream.readObject();

        /**
         * 得到Cipher对象对已用公钥加密的数据进行RSA解密
         */
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODEL);
        cipher.init(Cipher.DECRYPT_MODE, key);
        /**
         * 进行解密
         */
        byte[] bytes = cipher.doFinal(cryptograph);
        return new String(bytes);
    }

    public static void main(String[] args) throws Exception {
//        generateKeyPair();
//        String source = "hello world";
//        byte[] encrypt = encrypt(source);
//        /**
//         * 将密文进行base64编码进行传输
//         */
//        System.out.println(new String(Base64.encode(encrypt)));
//        String decrypt = decrypt(encrypt);
//        System.out.println(decrypt);
        Map<String, String> genKeyPair = genKeyPair();
        String publicKey = genKeyPair.get("publicKey");
        PublicKey publicKey1 = getPublicKey(publicKey);
        System.out.println(publicKey1.toString());
    }
}
