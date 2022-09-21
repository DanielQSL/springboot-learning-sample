package com.qsl.springboot.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ES配置
 *
 * @author qianshuailong
 * @date 2020/7/7
 */
@Configuration
public class ElasticsearchConfig {

    /**
     * 建立连接超时时间
     */
    public static int CONNECT_TIMEOUT_MILLIS = 1000;

    /**
     * 数据传输过程中的超时时间
     */
    public static int SOCKET_TIMEOUT_MILLIS = 30000;

    /**
     * 从连接池获取连接的超时时间
     */
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;

    /**
     * 路由节点的最大连接数
     */
    public static int MAX_CONN_PER_ROUTE = 10;

    /**
     * client最大连接数量
     */
    public static int MAX_CONN_TOTAL = 30;

    /**
     * ES集群节点，以 , 分隔多节点
     */
    @Value("${elasticsearch.clusterNodes}")
    private String clusterNodes;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    /**
     * The RequestOptions class holds parts of the request that should be shared between many requests in the same application.
     * make a singleton instance and share it between it between all requests.
     */
    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer" + TOKEN);
        // 默认缓存限制为100MB，此处修改为30MB。
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean(name = "restClient", destroyMethod = "close")
    public RestHighLevelClient restClient() {
        // 创建restClient的构造器
        RestClientBuilder restClientBuilder = RestClient.builder(loadHttpHosts());
        setTimeOutConfig(restClientBuilder);
        setConnectConfig(restClientBuilder);
        return new RestHighLevelClient(restClientBuilder);
    }

    /**
     * 加载ES集群节点，逗号分隔
     *
     * @return ES集群
     */
    private HttpHost[] loadHttpHosts() {
        String[] clusterNodesArray = clusterNodes.split(",");
        HttpHost[] httpHosts = new HttpHost[clusterNodesArray.length];
        for (int i = 0; i < clusterNodesArray.length; i++) {
            String clusterNode = clusterNodesArray[i];
            String[] hostAndPort = clusterNode.split(":");
            httpHosts[i] = new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
        }
        return httpHosts;
    }

    /**
     * 配置超时时间等参数
     *
     * @param restClientBuilder 创建restClient的构造器
     */
    private void setTimeOutConfig(RestClientBuilder restClientBuilder) {
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
    }

    /**
     * 使用异步httpclient时设置并发连接数、用户名密码
     *
     * @param restClientBuilder 创建restClient的构造器
     */
    private void setConnectConfig(RestClientBuilder restClientBuilder) {
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            // basic auth验证
            if (StringUtils.isNotBlank(username)) {
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(username, password));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpClientBuilder;
        });
    }

}
