package com.qsl.java.core.threadlocal.transmittablethreadlocal;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author DanielQSL
 * @date 2021/1/30
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

    @Bean("threadPoolExecutor")
    public ExecutorService threadPoolExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(
                10, 10,
                180, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("test-pool-%d")
                        .setUncaughtExceptionHandler((thread, throwable) -> log.error("test-pool got exception [{}]", thread, throwable))
                        .build(),
                new ThreadPoolExecutor.AbortPolicy());
        return TtlExecutors.getTtlExecutorService(executorService);
    }

}
