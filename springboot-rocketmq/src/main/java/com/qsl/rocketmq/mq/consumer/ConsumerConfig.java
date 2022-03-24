package com.qsl.rocketmq.mq.consumer;

import com.qsl.rocketmq.constants.RocketMqConstant;
import com.qsl.rocketmq.mq.config.RocketMqProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DanielQSL
 */
@Configuration
public class ConsumerConfig {

    @Autowired
    private RocketMqProperties rocketMqProperties;

    /**
     * 测试消费者
     *
     * @param testMqListener
     * @return
     * @throws MQClientException
     */
    @Bean("testConsumer")
    public DefaultMQPushConsumer testConsumer(TestMqListener testMqListener)
            throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketMqConstant.CREATE_ORDER_SUCCESS_CONSUMER_GROUP);
        consumer.setNamesrvAddr(rocketMqProperties.getNameServer());
        consumer.subscribe(RocketMqConstant.CREATE_ORDER_SUCCESS_TOPIC, "*");
        consumer.registerMessageListener(testMqListener);
        consumer.start();
        return consumer;
    }

}