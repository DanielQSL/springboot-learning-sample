package com.qsl.springboot.core.params_validate;

import java.util.regex.Pattern;

/**
 * IP工具类
 *
 * @author DanielQSL
 */
public class IpUtil {

    private static final String IPV4_REGEX = "(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))";

    private IpUtil() {

    }

    /**
     * 校验ip是否合法
     *
     * @param ip ip地址
     * @return 是否合法
     */
    public static boolean isValid(String ip) {
        return Pattern.matches(IPV4_REGEX, ip);
    }

}
