package com.qsl.springboot.service;

import com.qsl.springboot.dataobject.UserDO;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
public interface UserService {

    List<UserDO> list();
}
