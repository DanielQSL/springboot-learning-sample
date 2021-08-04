package com.qsl.springboot.clickhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动类
 *
 * @author DanielQSL
 */
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class SpringbootClickhouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootClickhouseApplication.class, args);
    }

}
