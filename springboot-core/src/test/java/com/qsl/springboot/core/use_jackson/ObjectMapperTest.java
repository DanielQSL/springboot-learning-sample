package com.qsl.springboot.core.use_jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

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

    @Test
    public void testJson2Object() throws IOException {
        String text = "{\"assignTime\":\"2021/01/20 05:51:10\",\"template\":{\"name\":\"muban1\",\"logo\":\"logo1\"},\"user_id\":NaN,\"test\":123}";
        Coupon coupon = objectMapper.readValue(text, Coupon.class);
        log.info("ObjectMapper: {}", coupon);
    }

}