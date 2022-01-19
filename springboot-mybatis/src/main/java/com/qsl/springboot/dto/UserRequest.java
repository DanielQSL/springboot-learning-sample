package com.qsl.springboot.dto;

import lombok.Data;

import java.util.List;

/**
 * 请求对象
 *
 * @author qianshuailong
 * @date 2020/9/16
 */
@Data
public class UserRequest {

    /** 标签结构名称 */
    private String labelName;

    /** 一级分类 */
    private Integer firstClassification;

    /** 二级分类 */
    private Integer secondClassification;

    /** 所属业务 */
    private Integer belongToBusiness;

    /** 权限列表 */
    private List<String> permissions;

    /** 创建开始时间（毫秒级时间戳） */
    private Long createStartTime;

    /** 创建结束时间（毫秒级时间戳） */
    private Long createEndTime;

    /** 页码 */
    private Integer pageNum;

    /** 每页大小 */
    private Integer pageSize;

}
