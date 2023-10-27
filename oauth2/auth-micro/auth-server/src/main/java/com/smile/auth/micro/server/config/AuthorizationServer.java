package com.smile.auth.micro.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @Description
 * @ClassName AuthorizationServer
 * @Author smile
 * @date 2023.10.22 15:52
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 将客户端信息存储到数据库
     *
     * @return
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService detailsService = new JdbcClientDetailsService(dataSource);
        detailsService.setPasswordEncoder(passwordEncoder);
        return detailsService;
    }

    /**
     * 客户端详情服务
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * 令牌管理服务
     *
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        /**
         * 客户端详情服务
         */
        service.setClientDetailsService(jdbcClientDetailsService());
        /**
         * 支持刷新令牌
         */
        service.setSupportRefreshToken(true);
        /**
         * 令牌存储策略
         */
        service.setTokenStore(tokenStore);

        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));

        service.setTokenEnhancer(tokenEnhancerChain);
        /**
         * 令牌默认有效期2小时
         */
        service.setAccessTokenValiditySeconds(7200);
        /**
         * 刷新令牌默认有效期3天
         */
        service.setRefreshTokenValiditySeconds(259200);
        return service;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                /**
                 * 认证管理器
                 */
                .authenticationManager(authenticationManager)
                /**
                 * 授权码服务
                 */
                .authorizationCodeServices(authorizationCodeServices)
                /**
                 * 令牌管理服务
                 */
                .tokenServices(tokenService())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        /**
         * 设置授权码模式的授权码如何存取
         */
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                /**
                 * oauth/token_key是公开
                 */
                .tokenKeyAccess("permitAll()")
                /**
                 * oauth/check_token公开
                 */
                .checkTokenAccess("permitAll()")
                /**
                 * 单认证（申请令牌）
                 */
                .allowFormAuthenticationForClients()
        ;
    }


}
