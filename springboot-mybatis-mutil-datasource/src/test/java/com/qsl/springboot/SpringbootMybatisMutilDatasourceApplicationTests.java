package com.qsl.springboot;

import com.qsl.springboot.dataobject.UserDO;
import com.qsl.springboot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisMutilDatasourceApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void listUser() {
        userService.list();
    }

}
