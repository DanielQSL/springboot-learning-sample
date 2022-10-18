package com.qsl.springboot.etcd.manager;

import cn.hutool.core.collection.CollectionUtil;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.watch.WatchEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ETCD 操作类
 *
 * @author DanielQSL
 */
@Slf4j
public class EtcdManager {

    private final Map<String, Watch.Watcher> watcherMap = new ConcurrentHashMap<>();

    private final Client client;

    public EtcdManager(Client client) {
        this.client = client;
    }

    /**
     * 根据指定的配置键获取对应的值
     *
     * @param key 键
     * @return 值
     * @throws Exception 异常信息
     */
    public String getValueByKey(String key) throws Exception {
        List<KeyValue> kvs = client.getKVClient().get(ByteSequence.from(key, StandardCharsets.UTF_8)).get().getKvs();
        if (CollectionUtil.isEmpty(kvs)) {
            return null;
        }
        return kvs.get(0).getValue().toString(StandardCharsets.UTF_8);
    }

    /**
     * 新增或者修改指定的配置键
     *
     * @param key   键
     * @param value 值
     */
    public void putValue(String key, String value) {
        client.getKVClient().put(ByteSequence.from(key, StandardCharsets.UTF_8), ByteSequence.from(value.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 删除指定的配置键
     *
     * @param key 键
     */
    public void deleteByKey(String key) {
        client.getKVClient().delete(ByteSequence.from(key, StandardCharsets.UTF_8));
    }

    /**
     * 监听指定key的变化
     *
     * @param keyStr 键
     */
    public void watchKey(String keyStr) {
        ByteSequence key = ByteSequence.from(keyStr, StandardCharsets.UTF_8);
        Watch.Watcher watch = client.getWatchClient().watch(key, listener -> {
            log.info("Watching for key: {}", key);
            listener.getEvents().forEach(event -> {
                // 操作类型
                WatchEvent.EventType eventType = event.getEventType();
                // 操作的键值对
                String key0 = event.getKeyValue().getKey().toString(StandardCharsets.UTF_8);
                String value0 = event.getKeyValue().getValue().toString(StandardCharsets.UTF_8);
                log.info("etcd watching type: {}, key: {}, value: {}", eventType, key0, value0);
                switch (event.getEventType()) {
                    case PUT:
                        break;
                    case DELETE:
                        // 如果是删除操作，把该key的Watcher关闭并从内存移除
                        Watch.Watcher removeWatcher = watcherMap.remove(keyStr);
                        removeWatcher.close();
                        break;
                    default:
                        break;
                }
            });
        });
        // 将这个Watcher放入内存中保存
        watcherMap.put(keyStr, watch);
    }

}
