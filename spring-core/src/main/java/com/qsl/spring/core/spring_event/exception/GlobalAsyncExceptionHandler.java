package com.qsl.spring.core.spring_event.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author qianshuailong
 * @date 2021/4/5
 */
@Slf4j
@Component
public class GlobalAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("[handleUncaughtException][method({}) params({}) 发生异常]",
                method, params, ex);
    }
}
