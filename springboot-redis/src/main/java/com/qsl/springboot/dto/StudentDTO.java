package com.qsl.springboot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author qianshuailong
 * @date 2020/7/6
 */
@JsonIgnoreProperties(ignoreUnknown = true) // 该注释用于反序列化时忽略不认识的字段
@Data
public class StudentDTO {

    private String name;

    private Integer age;
}
