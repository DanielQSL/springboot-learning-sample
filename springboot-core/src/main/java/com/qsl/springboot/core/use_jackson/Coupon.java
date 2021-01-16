package com.qsl.springboot.core.use_jackson;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author qianshuailong
 * @date 2021/1/16
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"couponCode", "status"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @JsonIgnore
    private Integer id;

    @JsonProperty("user_id")
    private Long userId;

    private String couponCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd hh:mm:ss")
    private Date assignTime;

    private CouponStatus status;

    private CouponTemplate template;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CouponTemplate {

        private String name;

        private String logo;
    }

    public static Coupon fake() {
        return new Coupon(1, 1234L, "manjian", new Date(), CouponStatus.USABLE,
                new CouponTemplate("muban1", "logo1"));
    }
}
