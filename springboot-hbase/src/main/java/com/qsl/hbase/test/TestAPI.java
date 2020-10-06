package com.qsl.hbase.test;

import com.alibaba.fastjson.JSON;
import com.qsl.hbase.utils.HBaseManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * API测试类
 *
 * @author qianshuailong
 * @date 2020/10/5
 */
@Slf4j
public class TestAPI {

    private static Connection connection = null;

    private static Admin admin = null;

    static {
        try {
            // 1.获取配置信息
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum", "localhost");
            configuration.set("hbase.zookeeper.property.clientPort", "2181");
            // 2.创建连接对象
            connection = ConnectionFactory.createConnection(configuration);
            // 3.创建Admin对象
            admin = connection.getAdmin();
        } catch (Exception e) {
            log.error("HBase connect error.", e);
        }
    }

    public static void main(String[] args) throws Exception {
        TableName[] tableNames = admin.listTableNames();
        System.out.println("结果：" + JSON.toJSONString(tableNames));
        // 关闭资源
        HBaseManager.close(connection, admin);
    }

}
