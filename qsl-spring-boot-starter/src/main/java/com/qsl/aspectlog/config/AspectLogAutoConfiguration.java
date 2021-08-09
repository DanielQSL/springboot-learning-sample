package com.qsl.aspectlog.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;

/**
 * 切面日志自动配置类
 *
 * @author qianshuailong
 * @date 2021/8/9
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableConfigurationProperties(AspectLogProperties.class)
@ConditionalOnProperty(prefix = "qsl.log", value = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
public class AspectLogAutoConfiguration implements PriorityOrdered {

    protected static final Logger logger = LoggerFactory.getLogger(AspectLogAutoConfiguration.class);

    @Around("@annotation(com.qsl.aspectlog.annotation.AspectLog) ")
    public Object isOpen(ProceedingJoinPoint joinPoint)
            throws Throwable {
        String methodName = joinPoint.getSignature()
                .toString().substring(
                        joinPoint.getSignature()
                                .toString().indexOf(" "),
                        joinPoint.getSignature().toString().indexOf("(")).trim();
        long time = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        logger.info("method:{} run:{} ms", methodName, (System.currentTimeMillis() - time));
        return result;
    }

    @Override
    public int getOrder() {
        // 保证事务等切面先执行
        return Integer.MAX_VALUE;
    }

}