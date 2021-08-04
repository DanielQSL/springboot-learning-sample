package com.qsl.springboot.clickhouse.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 测试 Mapper层
 *
 * @author DanielQSL
 */
@Mapper
public interface TestMapper {

    /**
     * 自定义语句查询SQL
     *
     * @param sqlStr sql语句
     * @return 用户ID集合
     */
    @Select("${sqlStr}")
    List<Long> selectDefinitionSql(@Param("sqlStr") String sqlStr);

}
