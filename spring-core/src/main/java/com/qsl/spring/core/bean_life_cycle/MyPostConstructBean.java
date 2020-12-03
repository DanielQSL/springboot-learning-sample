package com.qsl.spring.core.bean_life_cycle;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Spring 创建完 bean之后 (在启动之前)，便会立即调用 @PostConstruct 注解标记的方法
 * 可以使用@DependsOn
 *
 * @author qianshuailong
 * @date 2020/12/3
 */
@Component
public class MyPostConstructBean {

    @PostConstruct
    public void init() {
        System.out.println("Inside init() method...");
    }

}
