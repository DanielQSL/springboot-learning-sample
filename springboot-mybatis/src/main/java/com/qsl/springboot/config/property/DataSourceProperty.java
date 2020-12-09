package com.qsl.springboot.config.property;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * MyBaits数据源配置
 *
 * @author danielqsl
 * @date 2020/12/9
 */
@Data
public class DataSourceProperty {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
}
