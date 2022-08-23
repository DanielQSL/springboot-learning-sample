package com.qsl.springboot.core.aop.annotation;

import com.qsl.springboot.core.aop.aspect.EnumValueCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值检查
 *
 * @author DanielQSL
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumValueCheckValidator.class) // 指定验证器
@Documented
public @interface EnumValueCheck {

    /**
     * 默认的错误提示语
     */
    String message() default "未知枚举值";

    /**
     * 枚举的class
     */
    Class<? extends Enum> clz() default Enum.class;

    /**
     * 枚举值的字段名, 默认: value
     */
    String field() default "value";

    /**
     * 是否必填, 是否不能为空, 默认: false
     */
    boolean required() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumValueCheck[] value();
    }

}
