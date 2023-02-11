package com.smile.mdc.config;

import com.smile.mdc.wrapper.ThreadPoolExecutorMdcWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @ClassName ThreadPoolConfig
 * @Author smile
 * @date 2023.02.11 19:33
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutorMdcWrapper threadPoolExecutorMdcWrapper() {
        ThreadPoolExecutorMdcWrapper executorMdcWrapper = new ThreadPoolExecutorMdcWrapper(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),
                new ThreadFactory() {

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "log-");
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return executorMdcWrapper;
    }
}
