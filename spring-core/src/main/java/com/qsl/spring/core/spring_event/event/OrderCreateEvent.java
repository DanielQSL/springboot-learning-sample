package com.qsl.spring.core.spring_event.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 订单创建活动事件
 *
 * @author qianshuailong
 * @date 2021/4/5
 */
@Getter
@Setter
public class OrderCreateEvent extends ApplicationEvent {

    /**
     * 事件名称
     */
    private String name;

    /**
     * 消息参数
     */
    private List<String> contentList;

    public OrderCreateEvent(Object source, String name, List<String> contentList) {
        super(source);
        this.name = name;
        this.contentList = contentList;
    }
}
