package com.qsl.springboot.core.aop.annotation;

import com.qsl.springboot.core.aop.aspect.DateCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 日期检查
 *
 * @author DanielQSL
 */
@Documented
@Constraint(validatedBy = DateCheckValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(DateCheck.List.class)
public @interface DateCheck {

    String message() default "日期格式不正确";

    boolean required() default true;

    /**
     * 日期格式
     */
    String pattern() default "yyyy-MM-dd HH:mm:ss";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link DateCheck} annotations on the same element.
     *
     * @see DateCheck
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        DateCheck[] value();
    }

}
