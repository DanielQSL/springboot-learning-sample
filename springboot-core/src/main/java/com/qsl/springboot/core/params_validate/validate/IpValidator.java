package com.qsl.springboot.core.params_validate.validate;

import com.qsl.springboot.core.params_validate.IpUtil;
import com.qsl.springboot.core.params_validate.annotation.IpCheck;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * IP注解校验逻辑
 *
 * @author DanielQSL
 */
@Component
public class IpValidator implements ConstraintValidator<IpCheck, CharSequence> {

    @Override
    public boolean isValid(CharSequence ip, ConstraintValidatorContext constraintValidatorContext) {
        //验证逻辑
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        return IpUtil.isValid((String) ip);
    }

}
