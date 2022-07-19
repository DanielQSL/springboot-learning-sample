package com.qsl.springboot.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
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
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "latest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("max.poll.interval.ms", 5000);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));

        try {
            while (true) {
                // 超时时间
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.offset() + ", " + record.key() + ", " + record.value());
                }
                // 使用 Thread.sleep 模拟真实的消息处理逻辑
                Thread.sleep(6000L);
                consumer.commitSync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
