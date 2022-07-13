package com.qsl.springboot.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author shuailong.qian
 * @date 2022/7/12
 */
public class ConsumerDemo {

    public static void main(String[] args) {
        String topicName = "test-topic77";
        String groupId = "test-group2";

        Properties props = new Properties();
        props.put("bootstrap.servers", "172.19.21.59:9092");
        // 属于同一个组的消费实例，会负载消费消息
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.ineterval.ms", "1000");
        // 每次重启都是从最早的offset开始读取，不是接着上一次
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));

        try {
            while (true) {
                // 超时时间
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.offset() + ", " + record.key() + ", " + record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
