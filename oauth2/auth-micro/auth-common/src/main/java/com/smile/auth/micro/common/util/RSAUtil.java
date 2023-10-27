package com.smile.auth.micro.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Description
 * @ClassName RSAUtil
 * @Author smile
 * @date 2023.01.02 11:27
 */
public class RSAUtil {

    private static final int DEFAULT_KEY_SIZE = 2048;

    /**
     * 从文件中读取公钥
     *
     * @param filename
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static PublicKey getPublicKey(String filename) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        byte[] bytes = readFile(filename);
        return getPublicKey(bytes);
    }

    /**
     * 获取公钥
     *
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static PublicKey getPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        bytes = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从文件中读取私钥
     *
     * @param fileName
     * @return
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static PrivateKey getPrivateKey(String fileName) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bytes = readFile(fileName);
        return getPrivateKey(bytes);
    }

    /**
     * 获取密钥
     *
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        bytes = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * @param publicKeyFilename  公钥文件路径
     * @param privateKeyFilename 私钥文件路径
     * @param secret             生成密钥的密文
     * @param keySize
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        /**
         * 公钥
         */
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename, publicKeyBytes);
        /**
         * 私钥
         */
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename, privateKeyBytes);
    }


    private static byte[] readFile(String fileName) throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }
}
