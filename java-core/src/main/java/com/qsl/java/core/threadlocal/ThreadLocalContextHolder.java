package com.qsl.java.core.threadlocal;

import java.text.SimpleDateFormat;

/**
 * ThreadLocal使用
 *
 * @author qianshuailong
 * @date 2021/1/30
 */
public class ThreadLocalContextHolder {

    /**
     * 用法1
     */
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));


}
