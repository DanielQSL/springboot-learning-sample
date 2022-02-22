package com.qsl.springboot.core.aop.aspect;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 限速器切面处理类
 *
 * @author DanielQSL
 */
@Scope
@Aspect
@Component
public class RateLimitAop {

    @Autowired
    private HttpServletResponse response;

    /**
     * 每秒控制5个并发
     */
    private final RateLimiter rateLimiter = RateLimiter.create(5.0);

    @Pointcut("@annotation(com.qsl.springboot.core.aop.annotation.RateLimit)")
    public void serviceLimit() {

    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) {
        boolean flag = rateLimiter.tryAcquire();
        Object obj = null;
        try {
            if (flag) {
                obj = joinPoint.proceed();
            } else {
                // 封装错误结果返回
                String result = null;
//                result = JSONObject.fromObject(ResultUtil.success1(100, "failure")).toString();
                output(response, result);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("flag=" + flag + ",obj=" + obj);
        return obj;
    }

    public void output(HttpServletResponse response, String msg) throws IOException {
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/json;charset=UTF-8");
            out.write(msg.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}
