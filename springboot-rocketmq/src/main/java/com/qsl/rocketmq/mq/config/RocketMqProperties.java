package com.qsl.rocketmq.mq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RocketMQ配置信息
 *
 * @author DanielQSL
 */
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMqProperties {

    private String nameServer;

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

}