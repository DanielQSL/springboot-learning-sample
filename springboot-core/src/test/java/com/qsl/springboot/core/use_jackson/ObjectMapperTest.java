package com.qsl.springboot.core.use_jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author qianshuailong
 * @date 2021/1/16
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ObjectMapperTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUseJackson() throws Exception {
        Coupon coupon = Coupon.fake();
        log.info("ObjectMapper: {}", objectMapper.writeValueAsString(coupon));
    }

}