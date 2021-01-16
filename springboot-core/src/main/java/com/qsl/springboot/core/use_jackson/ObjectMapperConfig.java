package com.qsl.springboot.core.use_jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ObjectMapper配置类
 *
 * @author qianshuailong
 * @date 2021/1/7
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * 定义ObjectMapper配置
     * 因为new ObjectMapper()初始化很耗时，影响性能，避免重复创建，所以将他注入到bean中
     *
     * @return ObjectMapper
     */
    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        // 忽略json字符串中不识别的字段
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 关闭空对象不让序列化功能
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 设置可以解析带注释的JSON串
        om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 解析以"0"为开头的数字
        om.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        // 反序列化可以解析JSON串里包含了数字类型的属性值为NaN
        om.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        return om;
    }

}
