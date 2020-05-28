package com.qsl.springboot.dao.db1;

import com.qsl.springboot.dataobject.UserDO;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
public interface UserMapper {

    List<UserDO> list();
}
