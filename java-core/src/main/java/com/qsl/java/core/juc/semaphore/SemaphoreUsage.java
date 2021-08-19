package com.qsl.java.core.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量的使用
 *
 * @author DanielQSL
 */
public class SemaphoreUsage {

    /**
     * 正确使用方法，避免了【多释放】
     */
    public static void test01() {
        Semaphore semaphore = new Semaphore(10);
        boolean acquire = false;
        try {
            acquire = semaphore.tryAcquire(3, TimeUnit.SECONDS);
            if (acquire) {
                doSomeThing();
            } else {
                // doOtherThing();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (acquire) {
                semaphore.release();
            }
        }
    }

    public static void doSomeThing() {

    }

}
