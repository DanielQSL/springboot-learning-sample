package com.qsl.spring.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 应用上下文工具类
 *
 * @author qianshuailong
 * @date 2020/11/28
 */
@Slf4j
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (Objects.isNull(ApplicationContextUtil.applicationContext)) {
            ApplicationContextUtil.applicationContext = context;
        }
    }

    /**
     * 获取应用上下文
     *
     * @return 应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return ApplicationContextUtil.applicationContext;
    }

    /**
     * 通过name获取容器中的Bean
     *
     * @param name 名字
     * @return Bean对象
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz Bean的类型
     * @param <T>   类型
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name + class获取Bean
     *
     * @param name  名字
     * @param clazz 类型
     * @param <T>   类型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过name判断容器中的是否存在该Bean
     *
     * @param name 名字
     * @return 是否存在
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

}
