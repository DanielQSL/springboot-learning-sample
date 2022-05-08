package com.qsl.springboot.controller;

import com.qsl.springboot.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qianshuailong
 * @date 2020/7/6
 */
@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/write")
    public String write(@RequestParam("key") String key,
                        @RequestParam("value") String value) {
        redisTemplate.opsForValue().set(key, value);
        return "ok";
    }

//    @GetMapping("/write2")
//    public String write2() {
//        StudentDTO studentDTO = new StudentDTO();
//        studentDTO.setName("张三");
//        studentDTO.setAge(20);
//        redisTemplate.opsForValue().set("zhangsan", studentDTO);
//        StudentDTO zhangsan = (StudentDTO) redisTemplate.opsForValue().get("zhangsan");
//        return "ok";
//    }

}
