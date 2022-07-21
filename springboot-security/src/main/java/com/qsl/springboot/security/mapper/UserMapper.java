package com.qsl.springboot.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsl.springboot.security.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DanielQSL
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
