package com.qsl.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 布隆过滤器
 *
 * @author DanielQSL
 */
@Slf4j
@RequestMapping("/redisson/bf")
@RestController
public class BloomFilterController {

    @Autowired
    private RedissonClient redissonClient;

    private static final String BF_KEY = "bf-test";

    @GetMapping("/hello")
    public String hello() {
        final RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(BF_KEY);
        // 初始化布隆过滤器
        // expectedInsertions = 55000000
        // falseProbability = 0.03
        bloomFilter.tryInit(55000000L, 0.03);
        bloomFilter.add("zhangsan");
        bloomFilter.add("lisi");

        final boolean exist = bloomFilter.contains("zhangsan");
        System.out.println(exist);
        final long count = bloomFilter.count();
        System.out.println(count);

        return "OK";

    }

}
