package com.qsl.rocketmq.constants;

/**
 * RocketMQ 常量类
 *
 * @author DanielQSL
 */
public interface RocketMqConstant {

    /**
     * 完成订单创建发送事务消息 topic
     */
    String CREATE_ORDER_SUCCESS_TOPIC = "create_order_success_topic";

    /**
     * 完成订单创建 consumer 分组
     */
    String CREATE_ORDER_SUCCESS_CONSUMER_GROUP = "create_order_success_consumer_group";

    /**
     * 默认的producer分组
     */
    String ORDER_DEFAULT_PRODUCER_GROUP = "order_default_producer_group";

}
