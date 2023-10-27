package com.smile.auth.micro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * EnableWebFluxSecurity 开启security配置
 *
 * @Description
 * @ClassName WebSecurityConfig
 * @Author smile
 * @date 2023.10.21 05:47
 */
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity.authorizeExchange()
                /**
                 * 无需进行权限过滤的请求路径
                 */
                .pathMatchers("/auth/**", "/order/**", "/login.html", "/index.html").permitAll()
                /**
                 * option 请求默认放行
                 */
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated()
                .and().csrf().disable();

        return httpSecurity.build();
    }
}
