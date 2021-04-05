package com.qsl.spring.core.spring_event.service.impl;

import com.qsl.spring.core.spring_event.event.OrderCreateEvent;
import com.qsl.spring.core.spring_event.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用观察者模式，使创建订单和消息通知进行分离，低耦合。
 * 可以选择消息队列，spring事件机制等，本文选择Spring事件机制。
 *
 * @author qianshuailong
 * @date 2021/4/5
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void saveOrder() {
//        method1();
        method2();

    }

    /**
     * 同步监听
     */
    private void method2() {
        //1.创建订单
        log.info("订单创建成功");
        //2.发布事件
        List<String> contentList = new ArrayList<>();
        contentList.add("qsl");
        contentList.add("123456789");
        OrderCreateEvent orderCreateEvent = new OrderCreateEvent(this, "订单创建", contentList);
        //ApplicationContext是我们的事件容器上层，我们发布事件，也可以通过此容器完成发布
        applicationContext.publishEvent(orderCreateEvent);
        log.info("finished");
    }

    /**
     * 最原始使用
     */
    private void method1() {
        //1.创建订单
        log.info("订单创建成功");
        //2.发送短信
        log.info("恭喜您订单创建成功！----by sms");
        // 新需求：微信通知
        // 3.发送微信
        log.info("恭喜您订单创建成功！----by wechat");
    }

}
