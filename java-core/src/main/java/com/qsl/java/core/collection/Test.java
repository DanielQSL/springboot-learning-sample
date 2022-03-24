package com.qsl.java.core.collection;

/**
 * @author qianshuailong
 * @date 2021/10/5
 */
public class Test {

    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        System.out.println(list);
        list.remove(0);
        System.out.println(list);
        System.out.println(list.get(0));
    }

}
