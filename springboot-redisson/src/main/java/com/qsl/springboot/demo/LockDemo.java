package com.qsl.springboot.demo;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author qianshuailong
 * @date 2022/5/5
 */
public class LockDemo {

    public static void main(String[] args) throws InterruptedException {
        final RedissonClient redissonClient = getClient();

        RSemaphore semaphore = redissonClient.getSemaphore("semaphore");
        semaphore.trySetPermits(3);

        semaphore.acquire();

        semaphore.release();

    }

    private static void lock(RedissonClient redissonClient) throws Exception {
        RLock lock = redissonClient.getLock("test-lock");
        lock.lock();
        lock.tryLock(100, 10, TimeUnit.SECONDS);

        Thread.sleep(60000);

        lock.unlock();
    }

    private static void fairLock(RedissonClient redissonClient) throws Exception {
        RLock lock = redissonClient.getFairLock("test-lock");
        lock.lock();

        Thread.sleep(60000);

        lock.unlock();
    }

    private static void multiLock(RedissonClient redissonClient) throws Exception {
        RLock lock1 = redissonClient.getLock("test-lock1");
        RLock lock2 = redissonClient.getLock("test-lock2");
        RLock lock3 = redissonClient.getLock("test-lock3");

        RLock locks = redissonClient.getMultiLock(lock1, lock2, lock3);
        locks.lock();

        Thread.sleep(60000);

        locks.unlock();
    }

    private static void readWriteLock(RedissonClient redissonClient) {
        final RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("read-write-lock");
        final RLock readLock = readWriteLock.readLock();
        final RLock writeLock = readWriteLock.writeLock();
        readLock.lock();
        writeLock.lock();

        readLock.unlock();
        writeLock.unlock();
    }

    private static void semaphore(RedissonClient redissonClient) {
        RSemaphore semaphore = redissonClient.getSemaphore("semaphore");
        semaphore.trySetPermits(3);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]尝试获取Semaphore锁");
                    semaphore.acquire();
                    System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]成功获取到了Semaphore锁，开始工作");
                    Thread.sleep(3000);
                    semaphore.release();
                    System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]释放Semaphore锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void countDownLatch(RedissonClient redissonClient) throws Exception {
        RCountDownLatch latch = redissonClient.getCountDownLatch("anyCountDownLatch");
        latch.trySetCount(3);
        System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]设置了必须有3个线程执行countDown，进入等待中。。。");
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]在做一些操作，请耐心等待。。。。。。");
                    Thread.sleep(3000);
                    RCountDownLatch localLatch = redissonClient.getCountDownLatch("anyCountDownLatch");
                    localLatch.countDown();
                    System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]执行countDown操作");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        latch.await();
        System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]收到通知，有3个线程都执行了countDown操作，可以继续往下走");
    }

    private static RedissonClient getClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.8.210:6379")
                .setPassword("123456")
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(100)
                .setIdleConnectionTimeout(600000)
                .setSubscriptionConnectionMinimumIdleSize(10)
                .setSubscriptionConnectionPoolSize(100)
                .setTimeout(3000);
        config.setCodec(new StringCodec());
        config.setThreads(5);
        config.setNettyThreads(5);

        return Redisson.create(config);
    }

}
