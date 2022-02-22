package com.qsl.springboot.core.aop.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 *
 * @author DanielQSL
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    /**
     * 操作模块
     */
    String operModule() default "";

    /**
     * 操作类型
     */
    String operType() default "";

    /**
     * 操作说明
     */
    String operDesc() default "";
}
