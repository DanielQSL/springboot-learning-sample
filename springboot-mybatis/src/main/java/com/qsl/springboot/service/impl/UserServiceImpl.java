package com.qsl.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsl.springboot.dao.UserMapper;
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

    @Override
    public List<UserDO> page1(int pageNum, int pageSize) {
        // 第三个参数为 是否进行count查询
        PageHelper.startPage(pageNum, pageSize, false);
        List<UserDO> users = userMapper.list();
        log.info("users: {}", users);
        return users;
    }

    @Override
    public List<UserDO> page2(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserDO> users = userMapper.list();
        // PageInfo中包含了更多信息
        PageInfo<UserDO> page = new PageInfo<>(users);
        log.info("users: {}", page);
        return page.getList();
    }

    @Override
    public Long sum() {
        return null;
    }

}
