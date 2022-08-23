package com.qsl.springboot.core.aop.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.qsl.springboot.core.aop.annotation.EnumValueCheck;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 枚举值检查校验
 *
 * @author DanielQSL
 */
@Slf4j
public class EnumValueCheckValidator implements ConstraintValidator<EnumValueCheck, Object> {

    private Class<?> clz;

    private String field;

    private boolean allowEmpty;

    @Override
    public void initialize(EnumValueCheck constraintAnnotation) {
        this.clz = constraintAnnotation.clz();
        this.field = StrUtil.blankToDefault(constraintAnnotation.field(), "value");
        this.allowEmpty = !constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(field)) {
            return false;
        }
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
        }
        try {
            Method m = ReflectUtil.getMethodByName(clz, "values");
            Object[] enumObjs = ReflectUtil.invokeStatic(m);
            for (Object enumObj : enumObjs) {
                Object enumValue = ReflectUtil.invoke(enumObj, "get" + captureName(field));
                if (ObjectUtil.equal(value, enumValue)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable t) {
            log.error("EnumValueCheckValidator error", t);
            return false;
        }
    }

    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
