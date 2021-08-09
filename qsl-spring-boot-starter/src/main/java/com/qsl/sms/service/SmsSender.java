package com.qsl.sms.service;

/**
 * @author qianshuailong
 * @date 2021/8/9
 */
public interface SmsSender {

    /**
     * 发送短线
     *
     * @param message 短信内容
     * @return 是否发送成功
     */
    boolean send(String message);

}
