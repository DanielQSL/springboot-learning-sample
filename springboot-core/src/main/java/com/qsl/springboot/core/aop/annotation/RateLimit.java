package com.qsl.springboot.core.aop.annotation;

import java.lang.annotation.*;

/**
 * 限速器注解
 *
 * @author DanielQSL
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

}
