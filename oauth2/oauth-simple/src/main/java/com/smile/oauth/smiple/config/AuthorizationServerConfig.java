package com.smile.oauth.smiple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 授权服务配置类
 *
 * @author smile
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;


    @Value("${spring.application.name}")
    private String RESOURCE_ID;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                /**
                 * client_id
                 */
                .withClient("jim")
                .secret(passwordEncoder.encode("123456"))
                .resourceIds(RESOURCE_ID)
                // 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                .authorities("ROLE_TRUSTED_CLIENT")
                // 允许的授权范围
                .scopes("read", "write", "all")
                //true为自动认证，false跳转到授权页面
                .autoApprove(false)
                .redirectUris("http://www.baidu.com");
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                /**
                 * 是否允许任意应用访问公钥，JWT令牌才用非对称加密
                 */
                .tokenKeyAccess("permitAll()")
                /**
                 * /oauth/check_token：用于资源服务访问的令牌解析端点
                 * 是否允许任意应用访问 “/oauth/check_token”，注意：这里检查的是应用有没有认证，而非用户
                 * 比如当设置了isAuthenticated()，必须在请求头中添加应用的id和密钥才能访问
                 */
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authorizationCodeServices(authorizationCodeServices)
                /**
                 * 只有刷新令牌才会用到用户服务来验证是否已经登录
                 */
                .userDetailsService(userService);
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.DELETE, HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 通过tokenStore来定义Token的存储方式和生成方式：
     * InMemoryTokenStore
     * JdbcTokenStore
     * JwtTokenStore
     * RedisTokenStore
     * <p>
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * 授权码模式(authorization_code) 下需要设置此类，用于实现授权码逻辑
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
}
