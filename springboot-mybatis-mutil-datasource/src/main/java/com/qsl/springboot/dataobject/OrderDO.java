package com.qsl.springboot.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单表
 *
 * @author DanielQSL
 * @date 2020/5/28
 */
@Data
public class OrderDO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单流水号
     */
    private Integer orderNo;

    /**
     * 用户ID
     */
    private Integer userId;

}
