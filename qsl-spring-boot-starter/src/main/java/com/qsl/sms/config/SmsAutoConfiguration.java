package com.qsl.sms.config;

import com.qsl.sms.service.impl.AliyunSmsSenderImpl;
import com.qsl.sms.service.impl.TencentSmsSenderImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信自动配置类
 *
 * @author qianshuailong
 * @date 2021/8/9
 */
@EnableConfigurationProperties(value = SmsProperties.class)
@Configuration
public class SmsAutoConfiguration {

    /**
     * 阿里云发送短信的实现类
     *
     * @param smsProperties 短信属性
     * @return
     */
    @Bean
    public AliyunSmsSenderImpl aliYunSmsSender(SmsProperties smsProperties) {
        return new AliyunSmsSenderImpl(smsProperties.getAliyun());
    }

    /**
     * 腾讯云发送短信的实现类
     *
     * @param smsProperties 短信属性
     * @return
     */
    @Bean
    public TencentSmsSenderImpl tencentSmsSender(SmsProperties smsProperties) {
        return new TencentSmsSenderImpl(smsProperties.getTencent());
    }

}
