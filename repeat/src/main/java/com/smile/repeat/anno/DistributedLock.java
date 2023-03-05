package com.smile.repeat.anno;

import com.smile.repeat.util.LockKeyUtil;

import java.lang.annotation.*;

/**
 * 分布式锁防止重复提交
 *
 * @author 12780
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * 分布式锁默认开启
     *
     * @return
     */
    boolean enable() default true;

    /**
     * key的前缀
     *
     * @return
     */
    String prefix() default "";

    /**
     * 过期时间
     *
     * @return
     */
    int expire() default 15;

    /**
     * 重试次数
     *
     * @return
     */
    int retry() default 3;

    /**
     * 产生分布式锁的工具类
     *
     * @return
     */
    Class<?> clazzType() default LockKeyUtil.class;

    /**
     * 产生分布式锁的键
     *
     * @return
     */
    String methodName() default "generateLockKey";

    /**
     * 键的分隔符
     *
     * @return
     */
    String delimiter() default ":";

}
