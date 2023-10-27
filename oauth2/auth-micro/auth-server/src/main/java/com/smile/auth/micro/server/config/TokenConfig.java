package com.smile.auth.micro.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import java.lang.annotation.Retention;
import java.security.KeyPair;

/**
 * @Description
 * @ClassName TokenConfig
 * @Author smile
 * @date 2023.10.22 15:33
 */
@Configuration
public class TokenConfig {

    @Resource
    private RsaKeyProperties rsaKeyProperties;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyPair(rsaKeyProperties.getPublicKey(), rsaKeyProperties.getPrivateKey());
        converter.setKeyPair(keyPair);
        return converter;
    }
}
