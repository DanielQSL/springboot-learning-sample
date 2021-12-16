package com.qsl.java.core.stream;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author qianshuailong
 * @date 2020/11/16
 */
public class StreamUsage {

    private static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new Person("Tom", 20, 8900, "male", "New York"));
        personList.add(new Person("Jack", 30, 7000, "male", "Washington"));
        personList.add(new Person("Lily", 35, 7800, "female", "Washington"));
        personList.add(new Person("Anni", 28, 8200, "female", "New York"));
        personList.add(new Person("Owen", 40, 9500, "male", "New York"));
        personList.add(new Person("Alisa", 42, 7900, "female", "New York"));
    }

    public static void main(String[] args) {

    }

    /**
     * 遍历基础使用
     */
    private static void basicUsage() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
        // 匹配任意（适用于并行流）
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x < 6);
        System.out.println("匹配第一个值：" + findFirst.get());
        System.out.println("匹配任意一个值：" + findAny.get());
        System.out.println("是否存在大于6的值：" + anyMatch);
    }

    /**
     * 过滤使用
     */
    private static void filterUsage() {
        List<String> filterList = personList.stream().filter(x -> x.getSalary() > 8000)
                .map(Person::getName).collect(Collectors.toList());
        System.out.print("薪资高于8000的员工姓名：" + filterList);
    }

    /**
     * 聚合使用
     */
    private static void aggregationUsage() {
        // 案例一：获取String集合中最长的元素
        List<String> list1 = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max1 = list1.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max1.get());

        // 案例二：获取Integer集合中的最大值
        List<Integer> list2 = Arrays.asList(7, 6, 9, 4, 11, 6);
        // 自然排序
        Optional<Integer> max2 = list2.stream().max(Integer::compareTo);
        // 自定义排序
        Optional<Integer> max22 = list2.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序的最大值：" + max2.get());
        System.out.println("自定义排序的最大值：" + max22.get());

        // 案例三：获取员工工资最高的人。
        Optional<Person> max3 = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("员工工资最大值：" + max3.get().getSalary());
        // 排序（正序）
        personList.stream().sorted(Comparator.comparing(Person::getSalary));
        // 排序（倒序）
        personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed());

        // 案例四：计算Integer集合中大于6的元素的个数。
        List<Integer> list4 = Arrays.asList(7, 6, 4, 8, 2, 11, 9);
        long count = list4.stream().filter(x -> x > 6).count();
        System.out.println("list中大于6的元素个数：" + count);
    }

    /**
     * 对流扁平化使用
     */
    private static void flatMapUsage() {
        List<List<String>> list = new ArrayList<List<String>>() {{
            add(Lists.newArrayList("a", "b", "c"));
            add(Lists.newArrayList("d", "e", "f"));
            add(Lists.newArrayList("j", "k", "y"));
        }};
        // 结果：[[a, b, c], [d, e, f], [j, k, y]]
        System.out.println(list);
        List<String> collect = list.stream().flatMap(List::stream).collect(Collectors.toList());
        // 结果：[a, b, c, d, e, f, j, k, y]
        System.out.println(collect);
    }

    /**
     * 对象转Map
     */
    private static void toMap() {
        // 1、指定key-value，value是对象中的某个属性值。
        Map<String, Integer> map1 = personList.stream().collect(Collectors.toMap(Person::getName, Person::getSalary));
        // 2、指定key-value，value是对象本身，Function.identity()是简洁写法，也是返回对象本身（代替 User->User）
        Map<String, Person> map2 = personList.stream().collect(Collectors.toMap(Person::getName, Function.identity()));
        // 3、指定key-value，value是对象本身，Function.identity()是简洁写法，也是返回对象本身，key 冲突的解决办法，这里选择第二个key覆盖第一个key
        Map<String, Person> map3 = personList.stream().collect(Collectors.toMap(Person::getName, Function.identity(), (key1, key2) -> key2));
    }

}
