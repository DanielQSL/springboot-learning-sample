package com.qsl.springboot.controller;

import com.qsl.springboot.service.KafkaProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author shuailong.qian
 * @date 2022/7/12
 */
@RequestMapping("kafka")
@RestController
public class KafkaController {

    @Resource
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/send")
    public String send(@RequestParam("topic") String topic,
                       @RequestParam("msg") String msg) {
        kafkaProducerService.sendMessageSync(topic, msg);
        return "OK";
    }

    @GetMapping("/sendAsync")
    public String sendAsync(@RequestParam("topic") String topic,
                            @RequestParam("msg") String msg) {
        kafkaProducerService.sendMessageAsync(topic, msg);
        return "OK";
    }

}
