package com.qsl.springboot.controller;

import com.qsl.springboot.config.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author qianshuailong
 * @date 2020/7/5
 */
@Slf4j
@RequestMapping("/redisson")
@RestController
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisLock redisLock;

    private static final String LOCK = "my-lock";

    @GetMapping("/hello")
    public String hello() {
        // 1.获取一把锁，只要锁的名字一样，就是同一把锁
        RLock lock = redissonClient.getLock(LOCK);
        try {
            // 2.加锁
            lock.lock(); //阻塞式等待。默认加的锁都是30s时间
            // 1)锁的自动续期。如果业务超长，运行期间自动给锁续上新的30s，不用担心业务时间长，锁自动过期被删除
            // 2)加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁，锁默认在30s以后自动删除
            log.info("{} 加锁成功，执行业务...", Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            log.error("error", e);
        } finally {
            // 3.解锁
            log.info("{} 释放锁", Thread.currentThread().getName());
            lock.unlock();
        }

        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2() {
        // 1.获取一把锁，只要锁的名字一样，就是同一把锁
        RLock lock = redissonClient.getLock(LOCK);
        try {
            // 2.尝试加锁
            // waitTimeout 尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
            // leaseTime   锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间，确保在锁有效期内业务能处理完）
            boolean isSuccess = lock.tryLock(30, 30, TimeUnit.SECONDS);
            if (isSuccess) {
                log.info("{} 加锁成功，执行业务...", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(15);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            // 3.解锁
            log.info("{} 释放锁", Thread.currentThread().getName());
            lock.unlock();
        }
        return "hello";
    }

    @GetMapping("/hello3")
    public String hello3() {
        try {
            // 加锁
            boolean lock = redisLock.lock(LOCK);
            if (!lock) {
                throw new RuntimeException("加锁失败");
            }
            // do something
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            // 解锁
            redisLock.unlock(LOCK);
        }
        return "hello";
    }

}
