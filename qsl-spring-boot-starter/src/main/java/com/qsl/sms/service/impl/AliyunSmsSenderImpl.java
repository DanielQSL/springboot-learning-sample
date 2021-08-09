package com.qsl.sms.service.impl;

import com.qsl.sms.config.SmsProperties;
import com.qsl.sms.service.SmsSender;

/**
 * @author qianshuailong
 * @date 2021/8/9
 */
public class AliyunSmsSenderImpl implements SmsSender {

    private SmsProperties.SmsMessage smsMessage;

    public AliyunSmsSenderImpl(SmsProperties.SmsMessage smsProperties) {
        this.smsMessage = smsProperties;
    }

    @Override
    public boolean send(String message) {
        System.out.println(smsMessage.toString() + "阿里云开始发送短信==》短信内容：" + message);
        return true;
    }

}
