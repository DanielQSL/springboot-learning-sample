package com.qsl.springboot.core.async_task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author qianshuailong
 * @date 2021/1/7
 */
@Slf4j
@Service
public class AsyncService {

    @Async
    public void asyncProcess01() throws Exception {
        log.info("AsyncService start to process 01 -> {}", Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("AsyncService done to process 01 -> {}", Thread.currentThread().getName());
    }

    @Async
    public Future<String> asyncProcess02() throws Exception {
        log.info("AsyncService start to process 02 -> {}", Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("AsyncService done to process 02 -> {}", Thread.currentThread().getName());
        return new AsyncResult<>("qsl");
    }

    @Async
    public void asyncProcess03() throws Exception {
        log.info("AsyncService start to process 03 -> {}", Thread.currentThread().getName());
        Thread.sleep(2000);
        throw new RuntimeException("throw exception in asyncProcess03");
    }

}
