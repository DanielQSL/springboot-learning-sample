package com.qsl.springboot.etcd.config;

import com.qsl.springboot.etcd.manager.EtcdManager;
import io.etcd.jetcd.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ETCD 配置类
 *
 * @author DanielQSL
 */
@Configuration
public class EtcdConfig {

    @Value("${etcd.end-points:https://127.0.0.1:2379}")
    private String endPoints;

    @Bean(value = "client", destroyMethod = "close")
    public Client client() {
        return Client.builder().endpoints(endPoints.split(",")).build();
    }

    @Bean
    public EtcdManager etcdManager(@Autowired Client client) {
        return new EtcdManager(client);
    }

}
