package com.qsl.hbase.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HBase工具类
 *
 * @author qianshuailong
 * @date 2020/10/6
 */
@Component
public class HBaseManager {

    private static final Logger logger = LoggerFactory.getLogger(HBaseManager.class);

    private static Connection connection = null;

    @PostConstruct
    public void init() {
        // 1.获取配置信息
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "localhost");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        // 设置用户名密码
        configuration.set("hbase.client.username", "root");
        configuration.set("hbase.client.password", "root");
        try {
            // 2.创建连接对象
            connection = ConnectionFactory.createConnection(configuration);
        } catch (Exception e) {
            logger.error("HBase connect error.", e);
        }
    }

    @PreDestroy
    public void destroy() {
        // 关闭HBase连接
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (IOException e) {
                logger.error("HBase close connection error.", e);
            }
        }
    }

    /**
     * 创建命名空间
     *
     * @param nameSpace 命名空间名字
     * @throws IOException 异常
     */
    public static void createNameSpace(String nameSpace) throws IOException {
        // 1.构建命名空间描述器
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(nameSpace).build();
        // 2.获取Admin对象
        try (Admin admin = connection.getAdmin()) {
            // 3.创建命名空间
            admin.createNamespace(namespaceDescriptor);
        }
    }

    /**
     * 判断是否有表
     *
     * @param tableName 表名
     * @return 表是否存在
     * @throws IOException 异常
     */
    private static boolean isExistTable(String tableName) throws IOException {
        boolean exist;
        // 1.获取Admin对象
        try (Admin admin = connection.getAdmin()) {
            // 2.判断表是否存在
            exist = admin.tableExists(TableName.valueOf(tableName));
        }
        return exist;
    }

    /**
     * 创建表
     *
     * @param tableName    表名
     * @param maxVersions  版本
     * @param columnFamily 列族
     * @throws Exception 异常
     */
    public static void createTable(String tableName, int maxVersions, String... columnFamily) throws Exception {
        // 1.判断列族是否存在
        if (columnFamily.length == 0) {
            logger.error("请设置列族信息");
            return;
        }
        // 2.判断表是否存在
        if (isExistTable(tableName)) {
            logger.error("{} 表已存在", tableName);
            return;
        }
        // 3.添加列族信息
        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>(columnFamily.length);
        for (String cf : columnFamily) {
            ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf));
            columnFamilyDescriptorBuilder.setMaxVersions(maxVersions);
            ColumnFamilyDescriptor columnFamilyDescriptor = columnFamilyDescriptorBuilder.build();
            columnFamilyDescriptors.add(columnFamilyDescriptor);
        }
        // 4.创建表描述器
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName))
                .setColumnFamilies(columnFamilyDescriptors)
                .build();
        // 5.获取Admin对象
        try (Admin admin = connection.getAdmin()) {
            // 6.创建表
            admin.createTable(tableDescriptor);
        }
    }

    /**
     * 关闭资源
     *
     * @param connection 连接
     * @param admin      admin
     */
    public static void close(Connection connection, Admin admin) {
        if (admin != null) {
            try {
                admin.close();
            } catch (IOException e) {
                logger.error("HBase admin close error.", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                logger.error("HBase connection close error.", e);
            }
        }
    }

}
