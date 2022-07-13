package com.qsl.springboot.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author shuailong.qian
 * @date 2022/7/12
 */
public class ProducerDemo {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();

        // 这里可以配置几台broker即可，他会自动从broker去拉取元数据进行缓存
        props.put("bootstrap.servers", "172.19.21.59:9092");
        // 这个就是负责把发送的key从字符串序列化为字节数组
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 这个就是负责把你发送的实际的message从字符串序列化为字节数组
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 压缩消息，支持四种类型：none、lz4、gzip、snappy，默认为none。
        // 消费者默认支持解压，所以压缩设置在生产者，消费者无需设置。
        props.put("compression.type", "none");
        // 重试次数。默认0为不启用重试机制
        props.put("retries", 3);
        // 设置发送消息的缓冲区，默认值是33554432，就是32MB
        props.put("buffer.memory", 32 * 1024 * 1024);
        // 控制批处理大小，默认值是：16384，就是16KB。一般可以设置128KB
        props.put("batch.size", 128 * 1024);
        // 默认是0，即消息必须立即被发送，一般设置10毫秒或100毫秒
        props.put("linger.ms", 10);
        // 用来控制发送出去的消息的大小，默认是1048576，就是1MB。一般可设置10MB
        props.put("max.request.size", 10 * 1024 * 1024);
        // 发送一个请求出去后，有一个超时的时间限制，默认是30秒，超时则会抛异常
        props.put("request.timeout.ms", 30);
        // 请求的最长等待时间，默认60s。生产者空间不足时，send()被阻塞的时间
        props.put("max.block.ms", 3000);
        // 同步到副本（控制发送出去的消息的持久化机制）, 默认为1
        // acks=0 把消息发送到kafka就认为发送成功
        // acks=1 把消息发送到kafka leader分区，并且写入磁盘就认为发送成功
        // acks=all或-1 把消息发送到kafka leader分区，并且leader分区的副本follower对消息进行了同步就任务发送成功
        props.put("acks", "-1");

        // 构造Producer对象。
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>(
                "test-topic77", "test-key", "test-valueyyyyyyyyyyyyyyyyyy");

        // 异步发送的模式
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                // 消息发送成功
                System.out.println("消息发送成功");
            } else {
                // 消息发送失败，需要重新发送
                System.out.println("发送失败：" + exception.getMessage());
            }
        });

        Thread.sleep(10 * 1000);

        // 同步发送的模式
        // 你要一直等待人家后续一系列的步骤都做完，发送消息之后
        // 有了消息的回应返回给你，你这个方法才会退出来
//		producer.send(record).get();

        producer.close();
    }
}
