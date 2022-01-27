package com.qsl.springboot.service;

import com.qsl.springboot.dataobject.UserDO;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
public interface UserService {

    /**
     * 第一种分页方式
     *
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return 用户列表
     */
    List<UserDO> page1(int pageNum, int pageSize);

    /**
     * 第二种分页方式（PageInfo包含更多信息）
     *
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return 用户列表
     */
    List<UserDO> page2(int pageNum, int pageSize);

    /**
     * 求和
     *
     * @return
     */
    Long sum();

}
