package com.qsl.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * zk的使用
 *
 * @author DanielQSL
 */
public class LockUsage {

    private static CuratorFramework client;

    private static final String LOCK = "/qsl/lock/lock-1";

    static {
        client = CuratorFrameworkFactory.builder()
                .connectString("192.168.8.210:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
    }

    public static void main(String[] args) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, LOCK);
        lock.acquire();
        Thread.sleep(20000);
        lock.release();

        close();
    }

    private static void close() {
        client.close();
    }

}
