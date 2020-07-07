package com.qsl.springboot.repository;

import com.qsl.springboot.dataobject.ESProductDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qianshuailong
 * @date 2020/7/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test // 插入一条记录
    public void testInsert() {

    }

}