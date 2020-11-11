package com.qsl.java.core.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author qianshuailong
 * @date 2020/11/11
 */
@Data
public class UserDO {
    /** 主键 */
    private Long id;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
