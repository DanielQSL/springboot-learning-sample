package com.qsl.springboot.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsl.springboot.security.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author DanielQSL
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuMapper> {

    List<String> selectPermsByUserId(Long id);

}
