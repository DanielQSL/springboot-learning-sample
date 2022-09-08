package com.qsl.springboot.core.aop.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 方法上的参数校验切面
 *
 * @author DanielQSL
 */
@Slf4j
@Aspect
@Component
public class ParamsValidatorAspect {

    @Resource
    private Validator validator;

    /**
     * 切入点，@ParamsValidate 注解标注的
     */
    @Pointcut("@annotation(com.qsl.springboot.core.aop.annotation.ParamsValidate)")
    public void pointcut() {
    }

    /**
     * 环绕通知，在方法执行前后
     *
     * @param point 切入点
     * @return 结果
     * @throws Throwable 异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 签名信息
        Signature signature = point.getSignature();
        // 强转为方法信息
        MethodSignature methodSignature = (MethodSignature) signature;
        // 参数名称
        String[] parameterNames = methodSignature.getParameterNames();
        // 执行的对象
        Object target = point.getTarget();
        // 获取方法参数
        Object[] parameterValues = point.getArgs();
        if (log.isDebugEnabled()) {
            log.debug("请求处理方法: {}.{} 请求参数名: {}，请求参数值: {}", target.getClass().getName(), methodSignature.getMethod().getName(),
                    JSON.toJSONString(parameterNames), JSON.toJSONString(parameterValues));
        }

        // 参数校验
        for (Object parameterValue : parameterValues) {
            Set<ConstraintViolation<Object>> validateResult = validator.validate(parameterValue);
            if (CollectionUtil.isNotEmpty(validateResult)) {
                String errorMsg = validateResult.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("; "));
//                throw new BaseBizException(ErrorCodeEnum.PARAM_CHECK_ERROR, errorMsg.substring(0, errorMsg.length() - 1));
            }
        }
        return point.proceed();
    }

}
