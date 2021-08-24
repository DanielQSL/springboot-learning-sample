package com.qsl.springboot.core.params_validate.annotation;

import com.qsl.springboot.core.params_validate.validate.IpValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * IP校验注解
 *
 * @author DanielQSL
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IpValidator.class)
public @interface IpCheck {

    String message() default "IP格式不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

