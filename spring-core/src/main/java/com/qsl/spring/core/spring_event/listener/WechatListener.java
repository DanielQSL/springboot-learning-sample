package com.qsl.spring.core.spring_event.listener;

import com.qsl.spring.core.spring_event.event.OrderCreateEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 微信监听器
 * ApplicationListener是无序的
 *
 * @author qianshuailong
 * @date 2021/4/5
 */
//@Component
public class WechatListener implements ApplicationListener<OrderCreateEvent> {

//    @Async
    @Override
    public void onApplicationEvent(OrderCreateEvent event) {
        // 发送微信
        System.out.println(event.getContentList().get(0) + ",您的订单:" + event.getContentList().get(1) + "创建成功! ----by wechat");
    }
    
}
