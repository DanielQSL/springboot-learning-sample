package com.qsl.rocketmq.mq.consumer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 监听订单支付成功后的消息
 *
 * @author DanielQSL
 */
@Slf4j
@Component
public class TestMqListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt messageExt : list) {
                String message = new String(messageExt.getBody());
                log.info("收到消息：{}", message);
                log.info("消息详情：{}", JSON.toJSONString(messageExt));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.error("消费消息错误", e);
            //本地业务逻辑执行失败，触发消息重新消费
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

}