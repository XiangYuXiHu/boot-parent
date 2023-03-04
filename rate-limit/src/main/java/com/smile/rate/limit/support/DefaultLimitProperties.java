package com.smile.rate.limit.support;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @ClassName DefaultLimitProperties
 * @Author smile
 * @date 2023.03.04 15:18
 */
public class DefaultLimitProperties implements LimitProperties {

    private int replenishRate;

    private int burstCapacity;

    private TimeUnit timeUnit;

    public DefaultLimitProperties() {
    }

    public DefaultLimitProperties(int replenishRate, int burstCapacity, TimeUnit timeUnit) {
        this.replenishRate = replenishRate;
        this.burstCapacity = burstCapacity;
        this.timeUnit = timeUnit;
    }

    @Override
    public int replenishRate() {
        return replenishRate;
    }

    @Override
    public int burstCapacity() {
        return burstCapacity;
    }

    @Override
    public TimeUnit timeUnit() {
        return timeUnit;
    }
}
