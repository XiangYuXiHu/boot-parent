package com.smile.rate.limit.anno;

import com.smile.rate.limit.rule.KeyResolver;
import com.smile.rate.limit.rule.UriKeyResolver;
import com.smile.rate.limit.support.DefaultLimitProperties;
import com.smile.rate.limit.support.LimitProperties;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 12780
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 限流维度，默认使用uri进行限流
     *
     * @return
     */
    Class<? extends KeyResolver> keyResolver() default UriKeyResolver.class;

    /**
     * 限流配置，如果实现了该接口，默认以这个为准
     *
     * @return
     */
    Class<? extends LimitProperties> limitProperties() default DefaultLimitProperties.class;

    /**
     * 令牌桶每秒填充平均速率
     *
     * @return
     */
    int replenishRate() default 1;

    /**
     * 令牌桶总容量
     *
     * @return
     */
    int burstCapacity() default 5;

    /**
     * 限流时间维度，默认为秒
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
