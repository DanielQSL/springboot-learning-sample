package com.qsl.zookeeper.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * zk配置类
 *
 * @author DanielQSL
 */
@Component
public class ZooKeeperConfig {

    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework zookeeperClient() {
        return CuratorFrameworkFactory.builder()
                // 集群的话则是用,连接多个机器
                .connectString("192.168.8.210:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                //.namespace("test")
                .build();
    }

}
