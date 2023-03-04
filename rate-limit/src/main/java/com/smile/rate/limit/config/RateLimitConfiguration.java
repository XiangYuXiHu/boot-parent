package com.smile.rate.limit.config;

import com.smile.rate.limit.rule.KeyResolver;
import com.smile.rate.limit.rule.UriKeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

/**
 * @Description
 * @ClassName RateLimitConfiguration
 * @Author smile
 * @date 2023.03.04 15:36
 */
public class RateLimitConfiguration {

    public RateLimitConfiguration() {
    }

    @Bean(name = "rateLimitScript")
    public RedisScript<List<Long>> rateLimitScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("META-INF/script/redis_rate_limiter.lua")
        ));
        redisScript.setResultType(List.class);
        return redisScript;
    }

    @Bean
    public KeyResolver keyResolver() {
        return new UriKeyResolver();
    }
}
