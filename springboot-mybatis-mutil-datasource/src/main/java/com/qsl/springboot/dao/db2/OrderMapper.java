package com.qsl.springboot.dao.db2;

import com.qsl.springboot.dataobject.OrderDO;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
public interface OrderMapper {

    List<OrderDO> list();

}
