package com.qsl.springboot.core.aop.aspect;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.qsl.springboot.core.aop.annotation.DateCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 日期校验器
 *
 * @author DanielQSL
 */
public class DateCheckValidator implements ConstraintValidator<DateCheck, Object> {

    private String pattern;

    private boolean required;

    @Override
    public void initialize(DateCheck constraintAnnotation) {
        this.pattern = StrUtil.blankToDefault(constraintAnnotation.pattern(), DatePattern.NORM_DATETIME_PATTERN);
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return !required;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (StrUtil.isBlank(strVal)) {
                return !required;
            }
            try {
                if (strVal.length() != pattern.length()) {
                    return false;
                }
                DateUtil.parse(strVal, pattern);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
