package com.qsl.springboot.core.aop.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 *
 * @author qianshuailong
 * @date 2021/7/6
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    /**
     * 操作模块
     */
    String operModul() default "";

    /**
     * 操作类型
     */
    String operType() default "";

    /**
     * 操作说明
     */
    String operDesc() default "";
}
