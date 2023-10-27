package com.smile.auth.micro.common.anno;

import com.smile.auth.micro.common.config.OauthResourceServerAutoConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @Description
 * @ClassName EnableOauthResourceServer
 * @Author smile
 * @date 2023.10.21 06:52
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@EnableWebSecurity(debug = true) //打印security过滤器信息
@Retention(RetentionPolicy.RUNTIME)
@Import(OauthResourceServerAutoConfig.class)
public @interface EnableOauthResourceServer {
}
