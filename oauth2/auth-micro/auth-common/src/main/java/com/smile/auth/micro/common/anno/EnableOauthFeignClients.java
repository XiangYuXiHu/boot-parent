package com.smile.auth.micro.common.anno;

import com.smile.auth.micro.common.config.FeignInterceptorAutoConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description
 * @ClassName EnableOauthFeignClients
 * @Author smile
 * @date 2023.10.21 06:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
@Import(FeignInterceptorAutoConfig.class)
public @interface EnableOauthFeignClients {

    String[] value() default {};

    String[] basePackages() default {"com.smile.auth"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
