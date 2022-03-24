package com.qsl.rocketmq.controller;

import com.alibaba.fastjson.JSON;
import com.qsl.project.base.model.CommonResponse;
import com.qsl.rocketmq.constants.RocketMqConstant;
import com.qsl.rocketmq.model.TestMqMessage;
import com.qsl.rocketmq.mq.producer.DefaultProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DanielQSL
 */
@Slf4j
@RequestMapping("/mq")
@RestController
public class RocketMqController {

    @Autowired
    private DefaultProducer defaultProducer;

    @GetMapping("health")
    public CommonResponse<String> health() {
        return CommonResponse.success("OK");
    }

    @GetMapping("send")
    public CommonResponse<String> send(@RequestParam("id") String id) {
        log.info("发送mq请求");
        TestMqMessage message = new TestMqMessage();
        message.setOrderId(id);
        String msgJson = JSON.toJSONString(message);
        defaultProducer.sendMessage(RocketMqConstant.CREATE_ORDER_SUCCESS_TOPIC, msgJson, "订单已完成支付");
        return CommonResponse.success("OK");
    }

}
