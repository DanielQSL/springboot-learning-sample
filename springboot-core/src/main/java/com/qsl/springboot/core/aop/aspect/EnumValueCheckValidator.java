package com.qsl.springboot.core.aop.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.qsl.springboot.core.aop.annotation.EnumValueCheck;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;

/**
 * 枚举值检查校验
 *
 * @author DanielQSL
 */
@Slf4j
public class EnumValueCheckValidator implements ConstraintValidator<EnumValueCheck, Object> {

    private boolean required;

    private Class<? extends Enum<?>> enumClass;

    private String field;

    @Override
    public void initialize(EnumValueCheck constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.required = constraintAnnotation.required();
        this.enumClass = constraintAnnotation.clazz();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(field)) {
            return false;
        }
        if (Objects.isNull(value)) {
            return !required;
        }
        if (value instanceof Collection) {
            Collection<?> coll = (Collection<?>) value;
            if (CollUtil.isEmpty(coll)) {
                return !required;
            }
            for (Object obj : coll) {
                if (!isValid(obj, context)) {
                    return false;
                }
            }
            return true;
        } else {
            if (value instanceof String) {
                String strVal = (String) value;
                if (StrUtil.isBlank(strVal)) {
                    return !required;
                }
            }
            try {
                Method m = ReflectUtil.getMethodByName(enumClass, "values");
                Object[] enumObjs = ReflectUtil.invokeStatic(m);
                for (Object enumObj : enumObjs) {
                    Object enumValue = ReflectUtil.invoke(enumObj, "get" + captureName(field));
                    if (Objects.equals(value, enumValue)) {
                        return true;
                    }
                }
            } catch (Throwable t) {
                log.error("EnumValueCheckValidator error", t);
            }
            return false;
        }
    }

    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
