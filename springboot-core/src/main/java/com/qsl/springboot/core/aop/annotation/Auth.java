package com.qsl.springboot.core.aop.annotation;

import java.lang.annotation.*;

/**
 * @author qianshuailong
 * @date 2021/7/6
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {

    String value() default "";

}
