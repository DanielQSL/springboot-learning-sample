package com.qsl.java.core.juc.countdownlatch;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 使用
 *
 * @author DanielQSL
 */
public class CountDownLatchUsage {

    public static void main(String[] args) {
        timeout();
    }

    /**
     * 用法一：线程超时控制
     */
    public static void timeout() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new TestThread(countDownLatch, 1000).start();
        new TestThread(countDownLatch, 3000).start();
        new TestThread(countDownLatch, 5000).start();

        try {
            countDownLatch.await(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("线程已被中断");
            e.printStackTrace();
        }
    }

}

class TestThread extends Thread {

    private CountDownLatch countDownLatch;

    private Long timeout;

    public TestThread(CountDownLatch countDownLatch, long timeout) {
        this.countDownLatch = countDownLatch;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now() + " name =" + Thread.currentThread().getName());
        countDownLatch.countDown();
    }

}
