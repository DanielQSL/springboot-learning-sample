package com.qsl.springboot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author qianshuailong
 * @date 2021/1/7
 */
@EnableScheduling
@SpringBootApplication
public class SpringbootCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCoreApplication.class, args);
    }

}
