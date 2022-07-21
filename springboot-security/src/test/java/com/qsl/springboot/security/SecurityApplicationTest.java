package com.qsl.springboot.security;

import com.qsl.springboot.security.mapper.UserMapper;
import com.qsl.springboot.security.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author DanielQSL
 */
@SpringBootTest
public class SecurityApplicationTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));

        System.out.println(encoder.matches("123456", "$2a$10$j5oMaksrJZOrJuvOTA18.eqQISafnrifRvCpTgA.bZT3Kjgf2SpTC"));
    }

}
