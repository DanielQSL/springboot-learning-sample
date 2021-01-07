package com.qsl.springboot.core.async_task;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author qianshuailong
 * @date 2021/1/7
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncServiceTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void asyncProcess01() throws Exception {
        asyncService.asyncProcess01();
    }

    @Test
    public void asyncProcess02() throws Exception {
        Future<String> stringFuture = asyncService.asyncProcess02();
        log.info("async process 02 return: {}", stringFuture.get());
//        log.info("async process 02 return: {}", stringFuture.get(1, TimeUnit.SECONDS));
    }

    @Test
    public void asyncProcess03() throws Exception {
        asyncService.asyncProcess03();
        TimeUnit.SECONDS.sleep(3);
    }
}