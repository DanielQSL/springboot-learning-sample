package com.qsl.springboot.core.use_jackson;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠券状态
 *
 * @author qianshuailong
 * @date 2021/1/16
 */
@Getter
@AllArgsConstructor
public enum CouponStatus {

    /**
     * 优惠券状态
     */
    USABLE(1, "可用的"),
    USED(2, "使用过的"),
    ;

    private Integer code;
    private String desc;

}
