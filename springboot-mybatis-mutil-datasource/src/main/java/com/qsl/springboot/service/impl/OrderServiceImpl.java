package com.qsl.springboot.service.impl;

import com.qsl.springboot.dataobject.OrderDO;
import com.qsl.springboot.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public List<OrderDO> list() {
        return null;
    }
}
