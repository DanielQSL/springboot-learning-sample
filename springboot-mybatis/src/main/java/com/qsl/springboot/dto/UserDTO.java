package com.qsl.springboot.dto;

import lombok.Data;

/**
 * 用户返回对象
 *
 * @author qianshuailong
 * @date 2020/9/16
 */
@Data
public class UserDTO {
    /** 主键 */
    private Long id;

    /** 标签结构名称 */
    private String labelName;

    /** 标签结构英文名称 */
    private String labelNameEn;

    /** 标签类型 */
    private Integer labelType;

    /** 一级分类 */
    private Integer firstClassification;

    /** 二级分类 */
    private Integer secondClassification;

    /** 所属业务 */
    private Integer belongToBusiness;

    /** 标签值 */
    private String labelValue;

    /** 来源表 */
    private String sourceTable;

    /** 更新时间 */
    private Long updateTime;
}
