package com.qsl.spring.core.bean_life_cycle;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 等待SpringBoot启动完成之后开始做某些业务
 * 可使用@Order来排序
 * 应用：MQ消费者
 *
 * @author qianshuailong
 * @date 2020/12/03
 */
@Component
public class MyApplicationRunnerClient implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 容器启动后需要完成的业务
    }

}
