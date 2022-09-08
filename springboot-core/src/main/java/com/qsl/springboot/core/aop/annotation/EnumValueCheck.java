package com.qsl.springboot.core.aop.annotation;

import com.qsl.springboot.core.aop.aspect.EnumValueCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值检查
 *
 * @author DanielQSL
 */
@Documented
@Constraint(validatedBy = {EnumValueCheckValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(EnumValueCheck.List.class)
public @interface EnumValueCheck {

    String message() default "非法的枚举值";

    boolean required() default false;

    /**
     * 枚举的class
     */
    Class<? extends Enum<?>> clazz();

    /**
     * 枚举值的字段名
     */
    String field() default "value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link EnumValueCheck} annotations on the same element.
     *
     * @see EnumValueCheck
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumValueCheck[] value();
    }

}
