package com.qsl.spring.core.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取当前环境
 *
 * @author qianshuailong
 * @date 2020/12/4
 */
@RequestMapping("/env")
@RestController
public class EnvironmentController {

    /**
     * 当前环境
     * （由于测试环境数据库表不全，容易导致报错，所以增加一个环境判断）
     */
    @Value("${spring.profiles.active:}")
    private String env;

    @GetMapping
    public String getEnv() {
        return env;
    }

}
