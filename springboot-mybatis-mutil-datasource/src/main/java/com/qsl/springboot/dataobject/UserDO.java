package com.qsl.springboot.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户表
 *
 * @author DanielQSL
 * @date 2020/5/28
 */
@Data
public class UserDO {

    /**
     * 用户编号
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

}
