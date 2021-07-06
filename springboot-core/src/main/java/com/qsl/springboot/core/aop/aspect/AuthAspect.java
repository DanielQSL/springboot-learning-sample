package com.qsl.springboot.core.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author qianshuailong
 * @date 2021/7/6
 */
@Slf4j
@Aspect
@Component
public class AuthAspect {

    /**
     * 定义切点
     * 所有被Auth注解修饰的方法会织入advice
     */
    @Pointcut("@annotation(com.qsl.springboot.core.aop.annotation.Auth)")
    public void authAdvicePointCut() {

    }

    @Before("authAdvicePointCut()")
    public void doBefore() {
        log.info("日志");
    }

    @Around("authAdvicePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        log.info("方法名: {}", methodName);
        // 获取方法参数
        Object[] objects = joinPoint.getArgs();
//        Long id = ((JSONObject) objects[0]).getLong("id");
//        String name = ((JSONObject) objects[0]).getString("name");
        if (!validate()) {
            throw new Exception("无操作权限");
        }
        return joinPoint.proceed();
    }

    /**
     * 权限校验
     *
     * @return 是否通过
     */
    private boolean validate() {
        return true;
    }

}
