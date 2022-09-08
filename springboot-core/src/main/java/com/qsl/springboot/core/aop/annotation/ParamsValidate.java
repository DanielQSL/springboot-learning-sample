package com.qsl.springboot.core.aop.annotation;

import java.lang.annotation.*;

/**
 * 方法上的参数校验
 *
 * @author DanielQSL
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ParamsValidate {

}