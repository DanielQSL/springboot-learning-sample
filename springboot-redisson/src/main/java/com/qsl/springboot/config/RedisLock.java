package com.qsl.springboot.config;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁
 *
 * @author DanielQSL
 */
public class RedisLock {

    RedissonClient redissonClient;

    public RedisLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 互斥锁，seconds秒后自动失效
     *
     * @param key     键
     * @param seconds 过期时间（秒）
     */
    public boolean lock(String key, int seconds) {
        RLock rLock = redissonClient.getLock(key);
        if (rLock.isLocked()) {
            return false;
        }
        rLock.lock(seconds, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 互斥锁，自动续期
     *
     * @param key 键
     */
    public boolean lock(String key) {
        RLock rLock = redissonClient.getLock(key);
        if (rLock.isLocked()) {
            return false;
        }
        rLock.lock();
        return true;
    }

    /**
     * 手动释放锁
     *
     * @param key 键
     */
    public void unlock(String key) {
        RLock rLock = redissonClient.getLock(key);
        if (rLock.isLocked()) {
            rLock.unlock();
        }
    }

}
