package com.qsl.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短信属性
 *
 * @author qianshuailong
 * @date 2021/8/9
 */
@ConfigurationProperties(prefix = "qsl.sms")
public class SmsProperties {

    private SmsMessage aliyun = new SmsMessage();

    private SmsMessage tencent = new SmsMessage();

    public SmsMessage getAliyun() {
        return aliyun;
    }

    public void setAliyun(SmsMessage aliyun) {
        this.aliyun = aliyun;
    }

    public SmsMessage getTencent() {
        return tencent;
    }

    public void setTencent(SmsMessage tencent) {
        this.tencent = tencent;
    }

    @Override
    public String toString() {
        return "SmsProperties{" +
                "aliyun=" + aliyun +
                ", tencent=" + tencent +
                '}';
    }

    public static class SmsMessage {

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * 秘钥
         */
        private String sign;

        /**
         *
         */
        private String url;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "SmsMessage{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", sign='" + sign + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

}
