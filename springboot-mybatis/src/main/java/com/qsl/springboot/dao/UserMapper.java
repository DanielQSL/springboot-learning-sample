package com.qsl.springboot.dao;

import com.qsl.springboot.dataobject.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
@Repository
public interface UserMapper {

    List<UserDO> list();

}
