package com.qsl.springboot.demo.hyperloglog;

import com.qsl.springboot.constants.RedisConstant;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 去重统计，近似数
 *
 * @author qianshuailong
 * @date 2022/5/6
 */
public class Demo {

    private static Jedis jedis = new Jedis(RedisConstant.REDIS_HOST);

    static {
        jedis.auth(RedisConstant.REDIS_PWD);
    }

    public static void initUVData() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for (int i = 0; i < 4638; i++) {
            jedis.pfadd("hyperloglog_uv_" + today, String.valueOf((i + 1)));
        }
    }

    public static long getUVData() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return jedis.pfcount("hyperloglog_uv_" + today);
    }

    public static void main(String[] args) {
        initUVData();

        System.out.println(getUVData());
    }

}
