package com.smile.oauth.smiple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Description
 * @ClassName ResourceServerConfig
 * @Author smile
 * @date 2023.10.04 18:10
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.application.name}")
    private String RESOURCE_ID;

    @Autowired
    private TokenStore tokenStore;


    /**
     * HttpSecurity配置这个与Spring Security类似：
     * 请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是保护资源服务的全部路径。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                /**
                 * 配置需要保护的资源路径
                 */
                .antMatchers("/order/**")
                .and()
                .headers().addHeaderWriter((request, response) -> {
            response.addHeader("Access-Control-Allow-Origin", "*");//允许跨域
            /**
             * 如果是跨域的预检请求，则原封不动向下传达请求头信息
             */
            if (request.getMethod().equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            }
        });
    }

    /**
     * ResourceServerSecurityConfigurer主要配置以下几方面：
     * tokenServices：ResourceServerTokenServices 类的实例，用来实现令牌访问服务，如果资源服务和授权服务不在一块，就需要通过RemoteTokenServices来访问令牌
     * tokenStore：TokenStore类的实例，定义令牌的访问方式
     * resourceId：这个资源服务的ID
     * 其他的拓展属性例如 tokenExtractor 令牌提取器用来提取请求中的令牌。
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
                .tokenStore(tokenStore);
    }
}
