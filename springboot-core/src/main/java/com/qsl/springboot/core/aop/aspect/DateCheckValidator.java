package com.qsl.springboot.core.aop.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.qsl.springboot.core.aop.annotation.DateCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * 日期校验器
 *
 * @author DanielQSL
 */
public class DateCheckValidator implements ConstraintValidator<DateCheck, Object> {

    private String type;

    private boolean allowEmpty;

    @Override
    public void initialize(DateCheck constraintAnnotation) {
        this.type = StrUtil.blankToDefault(constraintAnnotation.type(), DatePattern.NORM_DATETIME_PATTERN);
        this.allowEmpty = constraintAnnotation.allowEmpty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return allowEmpty;
        }
        if (value instanceof Collection) {
            Collection<?> coll = (Collection<?>) value;
            if (CollUtil.isEmpty(coll)) {
                return allowEmpty;
            }
            for (Object o : coll) {
                boolean res = checkSingleObj(o);
                if (!res) {
                    return false;
                }
            }
            return true;
        } else {
            return checkSingleObj(value);
        }
    }

    public boolean checkSingleObj(Object value) {
        if (value instanceof String) {
            String strVal = (String) value;
            if (StrUtil.isBlank(strVal)) {
                return allowEmpty;
            }
            try {
                if (strVal.length() != type.length()) {
                    return false;
                }
                DateUtil.parse(strVal, type);
                return true;
            } catch (DateException e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
