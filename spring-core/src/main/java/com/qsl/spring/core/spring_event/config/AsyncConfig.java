package com.qsl.spring.core.spring_event.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.qsl.spring.core.spring_event.exception.GlobalAsyncExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步配置
 *
 * @author qianshuailong
 * @date 2021/4/5
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Autowired
    private GlobalAsyncExceptionHandler exceptionHandler;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return exceptionHandler;
    }

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 2);
        taskExecutor.setMaxPoolSize(60);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setThreadGroupName("Task66-");
        taskExecutor.setThreadNamePrefix("Async66-");
        taskExecutor.setBeanName("threadPoolTaskExecutor");
        return taskExecutor;
    }

    @Bean("threadPoolExecutor")
    public ExecutorService threadPoolExecutor() {
        return new ThreadPoolExecutor(
                5, 10,
                180, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("test-pool-%d")
                        .setUncaughtExceptionHandler((thread, throwable) -> log.error("test-pool got exception [{}]", thread, throwable))
                        .build(),
                new ThreadPoolExecutor.AbortPolicy());
    }

}
