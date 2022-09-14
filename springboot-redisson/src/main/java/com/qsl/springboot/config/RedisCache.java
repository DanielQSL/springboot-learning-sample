package com.qsl.springboot.config;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类
 *
 * @author DanielQSL
 */
public class RedisCache {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    // ============================ key相关操作 =============================

    /**
     * 设置缓存
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        if (timeout > 0) {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 获取缓存
     *
     * @param key 键
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

}
