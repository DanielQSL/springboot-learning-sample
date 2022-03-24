package com.qsl.rocketmq.mq.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ配置类
 *
 * @author DanielQSL
 */
@Configuration
@EnableConfigurationProperties(RocketMqProperties.class)
public class RocketMqConfig {

}