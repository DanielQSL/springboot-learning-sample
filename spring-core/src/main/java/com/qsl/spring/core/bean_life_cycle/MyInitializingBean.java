package com.qsl.spring.core.bean_life_cycle;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 与 @PostConstruct 解决方案非常相似，在 @PostConstruct 之前执行
 * 不推荐使用
 *
 * @author qianshuailong
 * @date 2020/12/3
 */
@Component
public class MyInitializingBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
