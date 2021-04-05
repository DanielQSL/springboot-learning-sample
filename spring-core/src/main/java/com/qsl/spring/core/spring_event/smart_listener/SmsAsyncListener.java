package com.qsl.spring.core.spring_event.smart_listener;

import com.qsl.spring.core.spring_event.event.OrderCreateEvent;
import com.qsl.spring.core.spring_event.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 短信监听器
 * SmartApplicationListener可以设置顺序等
 *
 * @author qianshuailong
 * @date 2021/4/5
 */
@Slf4j
@Component
public class SmsAsyncListener implements SmartApplicationListener {

    // 设置监听优先级 优先级越小，则越先被调用
    @Override
    public int getOrder() {
        return 2;
    }

    // 监听器智能所在之一，能够根据事件类型动态监听
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == OrderCreateEvent.class;
    }

    // 监听器智能所在之二，能够根据事件发布者类型动态监听
    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == OrderServiceImpl.class;
    }

    @Async
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrderCreateEvent event = (OrderCreateEvent) applicationEvent;
        // 发送短信
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 1 / 0;
        log.info(event.getContentList().get(0) + ",您的订单:" + event.getContentList().get(1) + "创建成功! ----by sms smart");
    }

}
