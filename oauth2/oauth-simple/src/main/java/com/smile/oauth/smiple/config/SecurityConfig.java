package com.smile.oauth.smiple.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解， 来判断用户对某个控制层的方法是否具有访问权限
 *
 * @EnableGlobalMethodSecurity(prePostEnabled=true) 使用表达式实现方法级别的安全性         4个注解可用
 * @PreAuthorize 在方法调用之前, 基于表达式的计算结果来限制对方法的访问
 * @PostAuthorize 允许方法调用, 但是如果表达式计算结果为false, 将抛出一个安全性异常
 * @PostFilter 允许方法调用, 但必须按照表达式来过滤方法的结果
 * @PreFilter 允许方法调用, 但必须在进入方法之前过滤输入值
 * @Description
 * @ClassName SecurityConfig
 * @Author smile
 * @date 2023.10.04 06:26
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /**
                 * 请求无需验证
                 */
                .antMatchers("/help").permitAll()
                .and().authorizeRequests()
                .antMatchers("/order/list").hasAnyAuthority("order:list")
                .antMatchers("/order/info").hasAnyAuthority("order:info")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated().and().csrf().disable()
                /**
                 * 内部注册 UsernamePasswordAuthenticationFilter
                 */
                .formLogin()
                /**
                 * 表单登录页面地址
                 */
                .loginPage("/login-view")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successForwardUrl("/login-success")
                .failureForwardUrl("/error")
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
