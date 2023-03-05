package com.smile.repeat.Service;

/**
 * 分布式锁
 *
 * @author zhangzhicheng
 */
public interface RedisService {

    /**
     * 获取分布式锁
     *
     * @param key
     * @param value
     * @param retries
     * @param expire
     * @return
     */
    Boolean tryLock(String key, String value, int retries, int expire);

    /**
     * 释放锁
     *
     * @param key
     * @param value
     */
    void release(String key, String value);
}
