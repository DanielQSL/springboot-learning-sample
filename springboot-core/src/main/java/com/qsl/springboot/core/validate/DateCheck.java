package com.qsl.springboot.core.validate;

import cn.hutool.core.date.DatePattern;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 日期检查
 *
 * @author DanielQSL
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DateCheckValidator.class) // 指定验证器
@Documented
public @interface DateCheck {

    /**
     * 默认的错误提示语
     */
    String message() default "日期格式不正确";

    /**
     * 日期格式, 默认: yyyy-MM-dd HH:mm:ss
     */
    String type() default DatePattern.NORM_DATETIME_PATTERN;

    /**
     * 是否允许空值, 默认: true
     */
    boolean allowEmpty() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        DateCheck[] value();
    }

}
