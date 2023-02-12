package com.smile;

import com.github.benmanes.caffeine.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @Description
 * @ClassName Ca
 * @Author smile
 * @date 2023.02.12 12:14
 */
@Slf4j
public class CaffeineTest {

    private Cache<String, Integer> cache;

    @Before
    public void initCache() {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .maximumSize(1000)
                .build();
    }

    /**
     * 手动加载
     */
    @Test
    public void manualTest() throws InterruptedException {
        String key = "china";
        /**
         * 如果缓存中不存指定的值，则方法将返回 null
         */
        log.info("{}", cache.getIfPresent(key));
        log.info("{}", cache.get(key, new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return 1;
            }
        }));
        cache.put(key, 100);
        log.info("{}", cache.getIfPresent(key));
//        cache.invalidate(key);
//        TimeUnit.SECONDS.sleep(10);
        log.info("{}", cache.getIfPresent(key));
    }

    private Integer getFromDB(String key) {
        return key.hashCode();
    }

    /**
     * 同步加载数据指的是，在get不到数据时最终会调用build构造时提供的CacheLoader对象中的load函数，
     * 如果返回值则将其插入缓存中，并且返回，这是一种同步的操作，也支持批量查找。
     */
    @Test
    public void syncTest() {
        LoadingCache<String, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .maximumSize(1000)
                .build(new CacheLoader<String, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(@NonNull String key) throws Exception {
                        return getFromDB(key);
                    }
                });
        log.info("{}", cache.get("china"));
    }

    /**
     * 异步加载
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void asyncTest() throws ExecutionException, InterruptedException {
        AsyncCache<String, Integer> asyncCache = Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .maximumSize(1000)
                .executor(Executors.newSingleThreadExecutor())
                .buildAsync();

        String key = "china";
        CompletableFuture<Integer> future = asyncCache.get(key, new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                log.info("当前线程:{}", Thread.currentThread().getName());
                return getFromDB(key);
            }
        });

        Integer value = future.get();
        log.info("{} --{}", value, Thread.currentThread().getName());
    }

    /**
     * 写后刷新不是方法名描述的那样在一定时间后自动刷新，而是在一定时间后进行了访问，再访问后才自动刷新。
     * 就是在第一次cache.get(1)的时候其实取到的依旧是旧值，在doAfterRead里边做了自动刷新的操作，
     * 这样在第二次cache.get(1)取到的才是刷洗后的值。
     *
     * @throws InterruptedException
     */
    @Test
    public void freshAfterWriteTest() throws InterruptedException {
        LoadingCache<String, Integer> cache = Caffeine.newBuilder()
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Integer>() {

                    @Nullable
                    @Override
                    public Integer load(@NonNull String key) throws Exception {
                        return getFromDB(key);
                    }
                });
        cache.put("china", 1);
        TimeUnit.MILLISECONDS.sleep(2500);
        log.info("{}", cache.getIfPresent("china"));
        TimeUnit.MILLISECONDS.sleep(1500);
        log.info("{}", cache.getIfPresent("china"));
        TimeUnit.MILLISECONDS.sleep(1500);
        log.info("{}", cache.getIfPresent("china"));
    }

    private Map<Integer, WeakReference<Integer>> secondCacheMap =
            new ConcurrentHashMap<>();

    /**
     * 二级缓存进行结合
     * 同步监听器:实现 CacheWriter 接口的两个方法，当新增，更新某个数据时，会同步触发 write 方法的执行。当删除某个数据时，会触发 delete 方法的执行。
     *
     * @throws InterruptedException
     */
    @Test
    public void CaffeineWriteTest() throws InterruptedException {
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .maximumSize(1)
                .writer(new CacheWriter<Integer, Integer>() {
                    @Override
                    public void write(@NonNull Integer key, @NonNull Integer value) {
                        secondCacheMap.put(key, new WeakReference<>(value));
                        log.info("触发cacheWriter.write,将key={}放入二级缓存中", key);
                    }

                    @Override
                    public void delete(@NonNull Integer key, @Nullable Integer value, @NonNull RemovalCause cause) {
                        switch (cause) {
                            case SIZE:
                                log.info("{}：缓存个数超过上限，触发删除", key);
                                break;
                            case EXPLICIT:
                                secondCacheMap.remove(key);
                                log.info("key:{}主动清除,从二级缓存清除", key);
                                break;
                            default:
                                break;
                        }
                    }
                }).build(new CacheLoader<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(@NonNull Integer key) throws Exception {
                        WeakReference<Integer> value = secondCacheMap.get(key);
                        if (null == value) {
                            return null;
                        }
                        log.info("从二级缓存读取key{}", key);
                        return value.get();
                    }
                });
        cache.put(1, 1);
        cache.put(2, 2);
        Thread.sleep(1000);
        log.info("{}-{}-{}-{}", cache.get(1), cache.stats().hitRate(), cache.stats().evictionCount(), cache.stats().averageLoadPenalty());
    }

    /**
     * 数据被淘汰的原因不外有以下几个：
     * <p>
     * EXPLICIT：如果原因是这个，那么意味着数据被我们手动的remove掉了。
     * REPLACED：就是替换了，也就是put数据的时候旧的数据被覆盖导致的移除。
     * COLLECTED：这个有歧义点，其实就是收集，也就是垃圾回收导致的，一般是用弱引用或者软引用会导致这个情况。
     * EXPIRED：数据过期，无需解释的原因。
     * SIZE：个数超过限制导致的移除。
     *
     * @throws InterruptedException
     */
    @Test
    public void removeListenTest() throws InterruptedException {
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<Integer, Integer>() {
                    @Override
                    public void onRemoval(@Nullable Integer key, @Nullable Integer value, @NonNull RemovalCause cause) {
                        log.info("淘汰通知key:{},原因:{}", key, cause);
                    }
                }).build(new CacheLoader<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(@NonNull Integer key) throws Exception {
                        return key;
                    }
                });

        cache.put(1, 1);
        cache.put(1, 2);
        cache.invalidate(1);
        cache.put(2, 3);
        TimeUnit.SECONDS.sleep(3);
        cache.getIfPresent(2);
    }
}
