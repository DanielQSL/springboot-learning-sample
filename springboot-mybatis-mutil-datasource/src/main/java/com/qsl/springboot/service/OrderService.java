package com.qsl.springboot.service;

import com.qsl.springboot.dataobject.OrderDO;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
public interface OrderService {

    List<OrderDO> list();

}
