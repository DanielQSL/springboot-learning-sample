package com.qsl.springboot.config;

import com.qsl.springboot.constants.KafkaConstants;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Producer 配置类
 *
 * @author DanielQSL
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Producer Template 配置
     */
    @Bean(name = "kafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Producer 工厂配置
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Producer 参数配置
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // 指定多个kafka集群多个地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_SERVERS);
        // 键的序列化方式
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 值的序列化方式
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 压缩消息，支持四种类型：none、lz4、gzip、snappy，默认为none。
        // 消费者默认支持解压，所以压缩设置在生产者，消费者无需设置。
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
        // 重试次数。默认0为不启用重试机制
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 设置发送消息的缓冲区，默认值是33554432，就是32MB
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 32 * 1024 * 1024);
        // 控制批处理大小，默认值是：16384，就是16KB。一般可以设置128KB
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 128 * 1024);
        // 默认是0，即消息必须立即被发送，一般设置10毫秒或100毫秒
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        // 用来控制发送出去的消息的大小，默认是1048576，就是1MB。一般可设置10MB
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 10 * 1024 * 1024);
        // 发送一个请求出去后，有一个超时的时间限制，默认是30秒，超时则会抛异常
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30);
        // 请求的最长等待时间，默认60s。生产者空间不足时，send()被阻塞的时间
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000);
        // 同步到副本（控制发送出去的消息的持久化机制）, 默认为1
        // acks=0 把消息发送到kafka就认为发送成功
        // acks=1 把消息发送到kafka leader分区，并且写入磁盘就认为发送成功
        // acks=all或-1 把消息发送到kafka leader分区，并且leader分区的副本follower对消息进行了同步就任务发送成功
        props.put(ProducerConfig.ACKS_CONFIG, "1");

        return props;
    }

}
