package com.smile.rate.limit.anno;

import com.smile.rate.limit.config.RateLimitConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 12780
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({RateLimitConfiguration.class})
public @interface EnableRateLimit {
}
