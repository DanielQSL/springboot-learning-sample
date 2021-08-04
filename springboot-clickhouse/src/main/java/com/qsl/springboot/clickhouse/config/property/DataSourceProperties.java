package com.qsl.springboot.clickhouse.config.property;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 数据源属性
 *
 * @author DanielQSL
 */
@Data
@Component
public class DataSourceProperties {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private Integer initialSize;

    private Integer minIdle;

    private Integer maxActive;

    private Integer maxWait;

    private Integer timeBetweenEvictionRunsMillis;

    private Integer minEvictableIdleTimeMillis;

    private String validationQuery;
}
