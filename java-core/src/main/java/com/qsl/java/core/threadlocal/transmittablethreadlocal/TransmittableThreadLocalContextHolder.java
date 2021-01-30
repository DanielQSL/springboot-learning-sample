package com.qsl.java.core.threadlocal.transmittablethreadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * TransmittableThreadLocal工具类
 * 可与线程池进行对象传递
 * 注意：使用时，需要对线程池进行包装，TtlExecutors.getTtlExecutor(executor)
 *
 * @author qianshuailong
 * @date 2021/1/30
 */
public class TransmittableThreadLocalContextHolder {

    private static final TransmittableThreadLocal<Map<String, Object>> threadLocalCache = new TransmittableThreadLocal<>();

    /**
     * 从ThreadLocal里获取缓存的值
     *
     * @param key 要获取的数据的KEY
     * @return 要获取的值
     */
    public static Object getCache(String key) {
        Map<String, Object> map = threadLocalCache.get();
        if (isCacheIsNull()) {
            return null;
        }
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    /**
     * 向ThreadLocal缓存值
     *
     * @param key   要缓存的KEY
     * @param value 要缓存的VALUE
     */
    public static void set(String key, Object value) {
        if (!isCacheIsNull()) {
            threadLocalCache.get().put(key, value);
        } else {
            Map<String, Object> vmap = Maps.newHashMap();
            vmap.put(key, value);
            threadLocalCache.set(vmap);
        }
    }

    /**
     * 根据KEY移除缓存里的数据
     *
     * @param key Key
     */
    public static void removeByKey(String key) {
        if (!isCacheIsNull()) {
            threadLocalCache.get().remove(key);
        }
    }

    /**
     * 移除当前线程缓存
     * 用于释放当前线程ThreadLocal资源
     */
    public static void remove() {
        threadLocalCache.remove();
    }

    /**
     * 判断ThreadLocal缓存是否为空
     *
     * @return 是否为空
     */
    private static boolean isCacheIsNull() {
        return Objects.isNull(threadLocalCache.get());
    }

    public static String cacheToString() {
        return isCacheIsNull() ? null : threadLocalCache.get().toString();
    }

}
