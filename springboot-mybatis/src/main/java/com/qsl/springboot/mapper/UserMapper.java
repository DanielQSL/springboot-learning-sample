package com.qsl.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsl.springboot.dataobject.UserDO;
import com.qsl.springboot.dto.UserDTO;
import com.qsl.springboot.dto.UserRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    List<UserDO> list();

    /**
     * XML演示使用
     *
     * @param request 请求参数
     * @return 集合
     */
    List<UserDO> listTestFormat(@Param("request") UserRequest request);

    /**
     * 批量保存演示
     *
     * @param list 集合
     */
    void savebatch(@Param("list") List<UserDTO> list);

}
