package com.qsl.springboot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author qianshuailong
 * @date 2020/7/6
 */
@Data
public class StudentDTO {

    private String name;

    private Integer age;
}
