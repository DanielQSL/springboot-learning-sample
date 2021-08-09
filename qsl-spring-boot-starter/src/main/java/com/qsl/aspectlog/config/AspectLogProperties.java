package com.qsl.aspectlog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 切面日志属性
 *
 * @author qianshuailong
 * @date 2021/8/9
 */
@ConfigurationProperties(prefix = "qsl.log")
public class AspectLogProperties {

    private Boolean enabled = false;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AspectLogProperties{" +
                "enabled=" + enabled +
                '}';
    }

}
