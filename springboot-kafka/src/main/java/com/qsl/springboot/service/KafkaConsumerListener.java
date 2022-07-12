package com.qsl.springboot.service;

import com.qsl.springboot.constants.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shuailong.qian
 * @date 2022/7/12
 */
@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = {KafkaConstants.TEST_TOPIC}, groupId = KafkaConstants.TEST_GROUP_ID_2, containerFactory = "kafkaListenerContainerFactory")
    public void kafkaListener(List<ConsumerRecord<String, String>> records) {
        System.out.println("===========" + records);
    }

//    @KafkaListener(topics = {KafkaConstants.TEST_TOPIC})
//    public void kafkaListener3(String record) {
//        System.out.println("===========" + record);
//    }

}
