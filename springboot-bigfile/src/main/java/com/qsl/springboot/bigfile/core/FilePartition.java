package com.qsl.springboot.bigfile.core;

import lombok.Data;

/**
 * 文件切分信息实体类
 *
 * @author DanielQSL
 */
@Data
public class FilePartition {

    /**
     * 文件切分起始位置
     */
    private Long start;

    /**
     * 文件切分终止位置
     */
    private Long end;

}
