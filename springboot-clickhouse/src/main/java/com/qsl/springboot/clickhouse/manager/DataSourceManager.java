package com.qsl.springboot.clickhouse.manager;

import com.qsl.springboot.clickhouse.utils.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 数据源 Manager
 *
 * @author DanielQSL
 */
@Slf4j
@Service
public class DataSourceManager {

    private static final String KEY_SEPARATOR = "-";

    private static final String VALIDATION_QUERY_SQL = "SELECT 1";

    private static final ConcurrentMap<Integer, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    /**
     * 创建数据源
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @param password        密码
     * @return 数据源
     */
    private DataSource createDataBase(List<Pair<String, Integer>> hostAndPortList, String database, String username, String password) throws SQLException {
        DataSource dataSource = JdbcUtil.getClickhouseDataSource(hostAndPortList, database, username, password);
        // 判断该数据源是否有效，无效下面测试语句则会抛错
        validationQuerySql(dataSource);
        return dataSource;
    }

    /**
     * 测试数据源连通性
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @param password        密码
     * @return 是否连接成功
     */
    public boolean testDataSourceConnection(List<Pair<String, Integer>> hostAndPortList, String database, String username, String password) {
        try {
            create(hostAndPortList, database, username, password);
            return true;
        } catch (SQLException ex) {
            log.error("testDataSourceConnection failed.", ex);
        }
        return false;
    }

    /**
     * 创建数据源并加入缓存
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @param password        密码
     * @return 数据源
     */
    public DataSource create(List<Pair<String, Integer>> hostAndPortList, String database, String username, String password) throws SQLException {
        DataSource dataSource = createDataBase(hostAndPortList, database, username, password);
        dataSourceMap.put(generateDataSourceKey(hostAndPortList, database, username), dataSource);
        log.info("create dataSource success. hostAndPorts: {}, dataBase: {}, username: {}", hostAndPortList, database, username);
        return dataSource;
    }

    /**
     * 创建数据源并加入缓存
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @param password        密码
     * @return 数据源
     */
    public DataSource createWithoutCache(List<Pair<String, Integer>> hostAndPortList, String database, String username, String password) throws SQLException {
        return createDataBase(hostAndPortList, database, username, password);
    }

    /**
     * 校验查询语句
     *
     * @param dataSource 数据源
     * @throws SQLException 异常
     */
    public void validationQuerySql(DataSource dataSource) throws SQLException {
        JdbcUtil.query(dataSource, VALIDATION_QUERY_SQL);
    }

    /**
     * 如果缓存中没有的话创建数据源
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @param password        密码
     * @return 数据源
     */
    public DataSource createIfNotExisted(List<Pair<String, Integer>> hostAndPortList, String database, String username, String password) throws SQLException {
        DataSource ds = get(hostAndPortList, database, username);
        if (Objects.nonNull(ds)) {
            return ds;
        }
        return create(hostAndPortList, database, username, password);
    }

    /**
     * 根据分片名获取对应数据源
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @return 数据源
     */
    public DataSource get(List<Pair<String, Integer>> hostAndPortList, String database, String username) {
        return dataSourceMap.get(generateDataSourceKey(hostAndPortList, database, username));
    }

    /**
     * 根据主机名和用户名删除对应数据源
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @return 数据源
     */
    public DataSource remove(List<Pair<String, Integer>> hostAndPortList, String database, String username) {
        return dataSourceMap.remove(generateDataSourceKey(hostAndPortList, database, username));
    }

    /**
     * 根据主机名和用户名判断是否存在对应数据源
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @return 是否存在
     */
    public boolean exist(List<Pair<String, Integer>> hostAndPortList, String database, String username) {
        return dataSourceMap.containsKey(generateDataSourceKey(hostAndPortList, database, username));
    }

    /**
     * 生成数据源键（如："127.0.0.1:8123-127.0.0.1:8123-db_test-tjpm".hashcode()）
     *
     * @param hostAndPortList 主机名和端口列表
     * @param database        数据库名
     * @param username        用户名
     * @return 数据源键
     */
    public int generateDataSourceKey(List<Pair<String, Integer>> hostAndPortList, String database, String username) {
        StringBuilder sb = new StringBuilder();
        for (Pair<String, Integer> pair : hostAndPortList) {
            sb.append(pair.getLeft()).append(":").append(pair.getRight()).append(KEY_SEPARATOR);
        }
        sb.append(database).append(KEY_SEPARATOR).append(username);
        return sb.toString().hashCode();
    }

}
