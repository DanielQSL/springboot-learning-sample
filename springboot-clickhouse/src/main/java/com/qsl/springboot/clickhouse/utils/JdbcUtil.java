package com.qsl.springboot.clickhouse.utils;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * JDBC工具类
 * <p>
 * 使用BalancedClickhouseDataSource
 * 使用@Deprecated修饰的是用druid线程池
 *
 * @author DanielQSL
 */
public class JdbcUtil {

    private static final String CLICKHOUSE_URL_FORMAT = "jdbc:clickhouse://%s:%d/%s";

    private static final String CLICKHOUSE_URL_FORMAT_V2 = "jdbc:clickhouse://%s/%s";

    private static final String JDBC_URL_FORMAT = "jdbc:clickhouse://%s:%d";

    private static final String HOST_AND_PORT_SEPARATOR = ":";

    private JdbcUtil() {
    }

    /**
     * 获取ClickHouse数据源
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @param password        密码
     * @return 数据源
     */
    public static DataSource getClickhouseDataSource(List<Pair<String, Integer>> hostAndPortList, String database, String username, String password) {
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser(username);
        properties.setPassword(password);
        properties.setDatabase(database);
        return new BalancedClickhouseDataSource(generateUrl(hostAndPortList, database), properties);
    }

    /**
     * 生成连接URL
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @return URL地址
     */
    private static String generateUrl(List<Pair<String, Integer>> hostAndPortList, String database) {
        StringBuilder finalUrl = new StringBuilder();
        for (Pair<String, Integer> pair : hostAndPortList) {
            if (StringUtils.isNotBlank(finalUrl.toString())) {
                finalUrl.append(",");
            }
            finalUrl.append(pair.getLeft()).append(HOST_AND_PORT_SEPARATOR).append(pair.getRight());
        }
        return String.format(CLICKHOUSE_URL_FORMAT_V2, finalUrl.toString(), database);
    }

    /**
     * 数据源属性配置
     */
    @Deprecated
    @Data
    public static class DataSourceProperty {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private Integer initialSize;
        private Integer minIdle;
        private Integer maxActive;
        private Integer maxWait;
        private String validationQuery;
    }

    /**
     * 获取ClickHouse数据源
     *
     * @param host                 主机名
     * @param port                 端口号
     * @param database             数据库名
     * @param username             用户名
     * @param password             密码
     * @param dataSourceProperties 数据源属性配置
     * @return 数据源
     */
    @Deprecated
    public static DataSource getClickhouseDataSource(String host, Integer port, String database, String username, String password, DataSourceProperty dataSourceProperties) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(generateUrl(host, port, database));
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(dataSourceProperties.getDriverClassName());
        datasource.setInitialSize(dataSourceProperties.getInitialSize());
        datasource.setMinIdle(dataSourceProperties.getMinIdle());
        datasource.setMaxActive(dataSourceProperties.getMaxActive());
        datasource.setMaxWait(dataSourceProperties.getMaxWait());
        datasource.setValidationQuery(dataSourceProperties.getValidationQuery());
        return datasource;
    }

    /**
     * 生成连接URL
     *
     * @param host     ip地址
     * @param port     端口
     * @param database 数据库名
     * @return URL地址
     */
    @Deprecated
    private static String generateUrl(String host, Integer port, String database) {
        return String.format(CLICKHOUSE_URL_FORMAT, host, port, database);
    }

    /**
     * 通用查询
     * 返回单条记录
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     * @return 查询结果
     */
    public static Object[] query(DataSource dataSource, String sql, Object... params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner.query(sql, new ArrayHandler(), params);
    }

    /**
     * 通用集合查询
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     * @return 查询结果
     */
    public static List<Object[]> list(DataSource dataSource, String sql, Object... params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner.query(sql, new ArrayListHandler(), params);
    }

    /**
     * 通用插入
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     * @return 插入成功行数
     */
    public static int save(DataSource dataSource, String sql, Object... params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner.update(sql, params);
    }

    /**
     * 批量插入
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     */
    public static void batchInsert(DataSource dataSource, String sql, List<Object[]> params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        queryRunner.batch(sql, params.toArray(new Object[params.size()][]));
    }

    /**
     * 通用删除
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     * @return 删除成功行数
     */
    public static int remove(DataSource dataSource, String sql, Object... params) throws SQLException {
        return save(dataSource, sql, params);
    }

    /**
     * 执行sql语句
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     */
    public static void execute(DataSource dataSource, String sql) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        queryRunner.execute(sql);
    }

    /**
     * 原生JDBC执行建表语句
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @throws SQLException 异常
     */
    public static void jdbcExecuteWithDataSource(DataSource dataSource, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    /**
     * 原生JDBC执行sql
     *
     * @param host     主机名
     * @param port     端口号
     * @param username 用户名
     * @param password 密码
     * @param sql      SQL语句
     * @throws SQLException 语句
     */
    public static void jdbcExecute(String host, Integer port, String username, String password, String sql) throws SQLException {
        String connectionStr = String.format(JDBC_URL_FORMAT, host, port);
        try (Connection connection = DriverManager.getConnection(connectionStr, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

}
