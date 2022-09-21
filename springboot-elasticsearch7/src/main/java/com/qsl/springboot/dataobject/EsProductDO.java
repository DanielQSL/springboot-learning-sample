package com.qsl.springboot.dataobject;

import lombok.Data;

/**
 * @author DanielQSL
 */
@Data
public class EsProductDO {

    /**
     * ID 主键
     */
    private Integer id;

    /**
     * SPU 名字
     */
    private String name;
    /**
     * 卖点
     */
    private String sellPoint;
    /**
     * 描述
     */
    private String description;
    /**
     * 分类编号
     */
    private Integer cid;
    /**
     * 分类名
     */
    private String categoryName;

}
