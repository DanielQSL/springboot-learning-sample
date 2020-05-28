package com.qsl.springboot.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源1配置
 *
 * @author DanielQSL
 * @date 2020/5/28
 */
@Configuration
@MapperScan(basePackages = "com.qsl.springboot.dao.db1", sqlSessionTemplateRef = "db1SqlSessionTemplate")
public class Db1DataSourceConfig {

    private static final String MAPPER_LOCATION = "classpath*:mapper/db1/*.xml";

    /**
     * 创建 db1 数据源  @Primary 注解声明为默认数据源
     */
    @Primary
    @Bean(name = "db1DataSource")
    @ConfigurationProperties("spring.datasource.db1")
    public DataSource db1DataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 创建 MyBatis SqlSessionFactory
     */
    @Primary
    @Bean(name = "db1SqlSessionFactory")
    public SqlSessionFactory data1SqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sqlSessionFactory.getObject();
    }

    /**
     * 创建 MyBatis SqlSessionTemplate
     */
    @Primary
    @Bean(name = "db1SqlSessionTemplate")
    public SqlSessionTemplate data1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 创建 db1 数据源的 TransactionManager 事务管理器
     */
    @Primary
    @Bean(name = "db1TransactionManager")
    public DataSourceTransactionManager db1TransactionManager(@Qualifier("db1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
