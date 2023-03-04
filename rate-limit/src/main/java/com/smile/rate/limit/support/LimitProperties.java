package com.smile.rate.limit.support;

import java.util.concurrent.TimeUnit;

/**
 * @author 12780
 */
public interface LimitProperties {

    /**
     * 令牌桶每秒填充平均速率
     *
     * @return
     */
    int replenishRate();

    /**
     * 令牌桶总容量
     *
     * @return
     */
    int burstCapacity();

    /**
     * 限流维度,默认秒
     * 支持s、m、h、d
     *
     * @return
     */
    TimeUnit timeUnit();
}
