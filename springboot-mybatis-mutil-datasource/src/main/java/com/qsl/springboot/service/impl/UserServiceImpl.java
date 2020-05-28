package com.qsl.springboot.service.impl;

import com.qsl.springboot.dao.db1.UserMapper;
import com.qsl.springboot.dao.db2.OrderMapper;
import com.qsl.springboot.dataobject.OrderDO;
import com.qsl.springboot.dataobject.UserDO;
import com.qsl.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<UserDO> list() {
        List<UserDO> users = userMapper.list();
        log.info("users: {}", users);
        List<OrderDO> orders = orderMapper.list();
        log.info("order: {}",orders);
        return users;
    }
}
