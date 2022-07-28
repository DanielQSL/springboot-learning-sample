package com.qsl.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qsl.springboot.mapper.UserMapper;
import com.qsl.springboot.dataobject.UserDO;
import com.qsl.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qianshuailong
 * @date 2020/5/28
 */
@Slf4j
@Service
public class UserMybatisPlusServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    public List<UserDO> page1(int pageNum, int pageSize) {
        String username = "zhangsan";
        Page<UserDO> page = new Page<>(pageNum, pageSize);
        Page<UserDO> pageResult = this.page(page, Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getUsername, username));
        return pageResult.getRecords();
    }

    @Override
    public List<UserDO> page2(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Long sum() {
        String username = "";
        LocalDateTime today = LocalDateTime.now();
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("ifnull(sum(amount),0) as amount");
        queryWrapper.eq(StringUtils.isNotBlank(username), "username", username)
                .gt("data_time", Date.from(today.atZone(ZoneId.systemDefault()).toInstant()));
        Map<String, Object> resultMap = this.getMap(queryWrapper);
        return Long.valueOf(String.valueOf(resultMap.get("amount")));
    }

}
