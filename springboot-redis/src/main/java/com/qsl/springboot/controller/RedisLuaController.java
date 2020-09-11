package com.qsl.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author qianshuailong
 * @date 2020/7/6
 */
@RequestMapping("/redis")
@RestController
public class RedisLuaController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Qualifier("incrAndExpireLuaScript")
    @Autowired
    private RedisScript<Long> incrAndExpireLuaScript;

    /**
     * 自增并设置过期时间lua脚本
     */
    private static final String INCR_EXPIRE_LUA_SCRIPT = "local v = redis.call('INCR', KEYS[1]) if v == 1 then redis.call('EXPIRE', KEYS[1], ARGV[1]) end return v";

    /**
     * 发送时间间隔（秒）
     */
    @Value("${black-industry.reply.number.sendTimeInterval:180}")
    private Long sendTimeInterval;

    /**
     * 发送违禁词频率限制
     */
    private static final int SEND_BLACK_WORD_FREQUENCY_LIMIT = 4;

    @GetMapping("/lua1")
    public String lua1(@RequestParam("key") String key,
                       @RequestParam("value") String value) {
        DefaultRedisScript<Long> incrAndExpireLuaScript = new DefaultRedisScript<>(INCR_EXPIRE_LUA_SCRIPT, Long.class);
        Long sendCount = redisTemplate.execute(incrAndExpireLuaScript, Collections.singletonList(key), sendTimeInterval);
        return "ok";
    }

    @GetMapping("/lua2")
    public String lua2(@RequestParam("key") String key,
                       @RequestParam("value") String value) {
        Long isOver = redisTemplate.execute(incrAndExpireLuaScript, Collections.singletonList(key), sendTimeInterval, SEND_BLACK_WORD_FREQUENCY_LIMIT - 1, System.currentTimeMillis() / 1000);
        return "ok";
    }

}
