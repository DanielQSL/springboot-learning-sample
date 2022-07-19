package com.qsl.springboot.service;

import com.qsl.springboot.constants.KafkaConstants;
import com.qsl.springboot.utils.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author shuailong.qian
 * @date 2022/7/12
 */
@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = {KafkaConstants.TEST_TOPIC}, groupId = KafkaConstants.TEST_GROUP_ID_2, containerFactory = "kafkaListenerContainerFactory1")
    public void kafkaListener(ConsumerRecords<String, String> records) {
        System.out.println("===========" + JsonUtil.toJsonString(records));
    }

//    @KafkaListener(topics = {KafkaConstants.TEST_TOPIC}, groupId = KafkaConstants.TEST_GROUP_ID_2, containerFactory = "kafkaListenerContainerFactory")
//    public void kafkaListenerSingle(String record) {
//        System.out.println("===========" + record);
//    }

}
