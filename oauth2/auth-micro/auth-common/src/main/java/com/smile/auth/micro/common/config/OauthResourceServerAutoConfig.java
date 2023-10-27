package com.smile.auth.micro.common.config;

import com.smile.auth.micro.common.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 资源服务的自动装配类，这里可以引入一个配置类
 * （使用@EnableConfigurationProperties），动态配置某些属性
 *
 * @Description
 * @ClassName OauthResourceServerAutoConfig
 * @Author smile
 * @date 2023.10.21 06:18
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnMissingBean(TokenStore.class)
public class OauthResourceServerAutoConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('read')")
                .and()
                .csrf().disable()
                .sessionManagement()
                /**
                 * 调整为让 Spring Security 不创建和使用 session
                 */
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        super.configure(resources);
        resources
                .resourceId("product_api")
                .tokenStore(tokenStore());
    }

    /**
     * Token 存放客户端需要注入JwtTokenStore类
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }


    /**
     * 增强器（Enhancer ）：什么是增强器呢，将权限等信息增加到一个普通token（比较短）中，这样直接拿这个token就能进行验证了，无需在请求再从其他存储中获取权限信息啦。
     * 转换器（Converter）：转换器就从当对JwtToken编码和解码的工作
     * <p>
     * <p>
     * JwtAccessTokenConverter是用来生成token的转换器，而token令牌默认是有签名的，
     * 且资源服务器需要验证这个签名。此处的加密及验签包括两种方式：
     * 对称加密、非对称加密（公钥密钥）
     * 对称加密需要授权服务器和资源服务器存储同一key值，而非对称加密可使用密钥加密，暴露公钥给资源服务器验签
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        try {
            File pubKeyFile = ResourceUtils.getFile("classpath:rsa/id_key_rsa.pub");
            PublicKey publicKey = RSAUtil.getPublicKey(pubKeyFile.getPath());
            RsaVerifier rsaVerifier = new RsaVerifier((RSAPublicKey) publicKey);
            converter.setVerifier(rsaVerifier);
        } catch (Exception e) {
            log.error("加载证书公钥文件出错：", e);
        }
        return converter;
    }

    @Bean
    @Primary
    @LoadBalanced
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
