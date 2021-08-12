package com.qsl.springboot.demo.hash;

import com.qsl.springboot.constants.RedisConstant;
import redis.clients.jedis.Jedis;

/**
 * 短网址追踪案例
 *
 * @author qianshuailong
 * @date 2021/8/12
 */
public class ShortUrlDemo {

    private Jedis jedis = new Jedis(RedisConstant.REDIS_HOST);

    /**
     * 在进制表示中的字符集合
     */
    private final static char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final String SHORT_URL_PREFIX = "https://t.cn/";

    public ShortUrlDemo() {
        // 发号器
        jedis.set("short_url_seed", "51177890045");
    }

    /**
     * 获取短连接网址
     */
    public String getShortUrl(String url) {
        // 发号器自增
        long shortUrlSeed = jedis.incr("short_url_seed");
        String shortUrlSuffix = toOtherBaseStr(shortUrlSeed, 62);

        jedis.hset("short_url_access_count", shortUrlSuffix, "0");
        jedis.hset("url_mapping", shortUrlSuffix, url);

        return SHORT_URL_PREFIX + shortUrlSuffix;
    }

    /**
     * 将10进制的数字转换到其他进制
     */
    private String toOtherBaseStr(long num, int base) {
        int charPos = 32;
        char[] buf = new char[32];
        while ((num / base) > 0) {
            buf[--charPos] = DIGITS[(int) (num % base)];
            num /= base;
        }
        buf[--charPos] = DIGITS[(int) (num % base)];
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * 给短连接地址进行访问次数的增长
     *
     * @param shortUrl
     */
    public void incrementShortUrlAccessCount(String shortUrl) {
        jedis.hincrBy("short_url_access_count", shortUrl, 1);
    }

    /**
     * 获取短连接地址的访问次数
     *
     * @param shortUrl
     */
    public long getShortUrlAccessCount(String shortUrl) {
        return Long.parseLong(jedis.hget("short_url_access_count", shortUrl));
    }

    public static void main(String[] args) {
        ShortUrlDemo demo = new ShortUrlDemo();

        String shortUrl = demo.getShortUrl("http://redis.com/index.html");
        System.out.println("页面上展示的短链接地址为：" + shortUrl);

        for (int i = 0; i < 152; i++) {
            demo.incrementShortUrlAccessCount(shortUrl);
        }

        long accessCount = demo.getShortUrlAccessCount(shortUrl);
        System.out.println("短链接被访问的次数为：" + accessCount);
    }

}
