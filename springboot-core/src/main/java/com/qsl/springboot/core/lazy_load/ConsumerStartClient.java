package com.qsl.springboot.core.lazy_load;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * MQ消费者等待Spring容器启动完成之后再开始消费
 * 原因：MQ直接在配置类中启动会导致，开始消费时，SpringCloud相关组件还未加载完成使得微服务调用失败
 *
 * @author DanielQSL
 * @date 2020/12/1
 */
@Component
public class ConsumerStartClient implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        if (ApplicationContextUtil.containsBean(AliConfig.THREAD_REPLY_CONSUMER_BEAN_NAME)) {
//            Consumer threadReplyConsumer = ApplicationContextUtil.getBean(AliConfig.THREAD_REPLY_CONSUMER_BEAN_NAME, Consumer.class);
//            threadReplyConsumer.start();
//        }
//        if (ApplicationContextUtil.containsBean(AliConfig.BLACK_INDUSTRY_IDENTIFY_CONSUMER_BEAN_NAME)) {
//            Consumer blackIndustryIdentifyConsumer = ApplicationContextUtil.getBean(AliConfig.BLACK_INDUSTRY_IDENTIFY_CONSUMER_BEAN_NAME, Consumer.class);
//            blackIndustryIdentifyConsumer.start();
//        }
    }

}
