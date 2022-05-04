package com.qsl.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * zk的使用
 * https://segmentfault.com/a/1190000023566327
 *
 * @author DanielQSL
 */
public class ZooKeeperCrudUsage {

    private static CuratorFramework client;

    private static final String PATH = "/qsl/curd/node-1";

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
//        create();

//        update();

        query();

        close();
    }

    private static void create() throws Exception {
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(PATH, "world".getBytes(StandardCharsets.UTF_8));
    }

    private static void query() throws Exception {
        byte[] bytes = client.getData().forPath(PATH);
        System.out.println(new String(bytes));
    }

    private static void update() throws Exception {
        client.setData()
                .forPath(PATH, "hello world".getBytes(StandardCharsets.UTF_8));
    }

    private static void queryChildren() throws Exception {
        List<String> children = client.getChildren().forPath(PATH);
        System.out.println(children);
    }

    private static void delete() throws Exception {
        client.delete().forPath(PATH);
    }

    private static void asyncCreate() throws Exception {
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .inBackground((c, e) -> {
                    // 创建成功后回调函数
                    System.out.println("=========");
                    System.out.println(e);
                }).forPath(PATH);
    }

    private static void watch() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Watcher w = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
                countDownLatch.countDown();
            }
        };
        client.getData().usingWatcher(w).forPath(PATH);
        countDownLatch.await();
    }

    private static void close() {
        client.close();
    }

}
