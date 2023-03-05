package com.smile.repeat.util;

import com.alibaba.fastjson.JSON;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * redisKey生成器
 */
public class LockKeyUtil {

    /**
     * lock key
     *
     * @param param
     * @return
     */
    public static String generateLockKey(Object param) {
        if (Objects.isNull(param)) {
            return UUID.randomUUID().toString();
        }
        return DigestUtils.md5DigestAsHex(JSON.toJSONString(param).getBytes(StandardCharsets.UTF_8));
    }
}
