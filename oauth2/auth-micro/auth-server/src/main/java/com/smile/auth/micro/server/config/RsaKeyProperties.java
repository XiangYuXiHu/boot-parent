package com.smile.auth.micro.server.config;

import com.smile.auth.micro.common.util.RSAUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @Description
 * @ClassName RsaKeyProperties
 * @Author smile
 * @date 2023.10.22 15:34
 */
@Component
public class RsaKeyProperties {

    public URL pubKeyFile = RsaKeyProperties.class.getClassLoader().getResource("rsa/id_key_rsa.pub");
    public URL priKeyFile = RsaKeyProperties.class.getClassLoader().getResource("rsa/id_key_rsa");

    public PublicKey publicKey;
    public PrivateKey privateKey;

    @PostConstruct
    public void getPairKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        publicKey = RSAUtil.getPublicKey(pubKeyFile.getFile());
        privateKey = RSAUtil.getPrivateKey(priKeyFile.getFile());
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
