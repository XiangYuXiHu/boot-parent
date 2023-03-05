package com.smile.repeat.Service.impl;

import com.smile.repeat.Service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangzhicheng zhangzhicheng@tuhu.cn
 * @create 2020/6/22 9:59
 * @since JDK8
 */
@Slf4j
@Component("redisDistributedLock")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end ";


    @Override
    public Boolean tryLock(String key, String value, int retries, int expire) {
        while (retries > 0) {
            try {
                Boolean isSuccess = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                    return connection.set(key.getBytes(Charset.forName("utf-8")),
                            value.getBytes(Charset.forName("utf-8")),
                            Expiration.seconds(expire),
                            SetOption.SET_IF_ABSENT);
                });
                if (isSuccess) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                log.error("获取分布式锁异常:{}", e.getMessage());
            }

            /**
             * 获取分布式锁异常重试，获取分布式锁失败直接返回
             */
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void release(String key, String value) {
        try {
            List<String> keys = Collections.singletonList(key);
            DefaultRedisScript redisScript = new DefaultRedisScript<>();
            redisScript.setScriptText(script);
            redisScript.setResultType(Long.class);
            redisTemplate.execute(redisScript, keys, value);
        } catch (Exception e) {
            log.error("释放锁失败,redisKey={}", key, e);
        }
    }
}
