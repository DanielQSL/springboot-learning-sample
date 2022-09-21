package com.qsl.springboot.demo;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.qsl.springboot.config.ElasticsearchConfig;
import com.qsl.springboot.model.TestData;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 常用操作
 *
 * @author DanielQSL
 */
@Slf4j
@Component
public class EsUsage {

    private static final String TEST_INDEX = "test1";

    @Resource
    public RestHighLevelClient restClient;

    /**
     * 创建索引
     */
    public void createIndex(String index) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        // 设置分片和副本数量，默认 分片5 副本1
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 5)
                .put("index.number_of_replicas", 1)
        );
        createIndexRequest.mapping("{\n"
                + "            \"properties\":{\n"
                + "                \"name\":{\n"
                + "                    \"type\":\"text\"\n"
                + "                }\n"
                + "            }\n"
                + "        }", XContentType.JSON);
        CreateIndexResponse createIndexResponse = restClient.indices().create(createIndexRequest, ElasticsearchConfig.COMMON_OPTIONS);
        log.info("create index {} response:{}", index, createIndexResponse.isAcknowledged());
    }

    /**
     * 删除索引
     */
    private void deleteIndex(String index) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        try {
            // 异步删除索引
            ActionListener<AcknowledgedResponse> listener = new ActionListener<AcknowledgedResponse>() {
                @Override
                public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                    log.info("异步删除索引成功 index: {} response: {}", index, JSON.toJSONString(acknowledgedResponse));
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("异步删除索引失败 index: {}", index, e);
                }
            };
            restClient.indices().deleteAsync(deleteIndexRequest, ElasticsearchConfig.COMMON_OPTIONS, listener);
        } catch (RuntimeException e) {
            log.error("delete index exception", e);
        }
    }

    /**
     * 导入数据
     */
    private void insertData(TestData data) throws IOException {
        IndexRequest indexRequest = new IndexRequest(TEST_INDEX);
        // 设置ID，不设置则默认生成UUID
        indexRequest.id(data.getId().toString());
        // 强制刷新数据
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        indexRequest.source(JSON.toJSONString(data), XContentType.JSON);
        IndexResponse response = restClient.index(indexRequest, ElasticsearchConfig.COMMON_OPTIONS);
        Assert.isTrue(RestStatus.CREATED.equals(response.status()));
    }

    /**
     * 精确查询
     */
    private void queryById(TestData expectedData) throws IOException {
        SearchRequest searchRequest = new SearchRequest(TEST_INDEX);
        searchRequest.source(new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("id", expectedData.getId()))
        );
        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
        Assert.isTrue(RestStatus.OK.equals(searchResponse.status()));

        // 查询条数为1
        SearchHits hits = searchResponse.getHits();
        Assert.isTrue(1 == hits.getTotalHits().value);

        // 判断查询数据和插入数据是否相等
        String dataJson = hits.getHits()[0].getSourceAsString();
        Assert.isTrue(JSON.toJSONString(expectedData).equals(dataJson));
    }

    /**
     * 更新数据
     */
    public void update() throws IOException {
        // 插入数据
        TestData data = new TestData(3, "测试数据03", 29);
        this.insertData(data);

        // 更新数据
        data.setName("测试数据被更新");
        UpdateRequest updateRequest = new UpdateRequest(TEST_INDEX, data.getId().toString());

        updateRequest.doc(JSON.toJSONString(data), XContentType.JSON);
        // 强制刷新数据
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        UpdateResponse updateResponse = restClient.update(updateRequest, ElasticsearchConfig.COMMON_OPTIONS);
        Assert.isTrue(RestStatus.OK.equals(updateResponse.status()));
    }

    /**
     * 删除数据
     */
    public void delete(String id) throws IOException {
        // 删除数据
        DeleteRequest deleteRequest = new DeleteRequest(TEST_INDEX, id);
        // 强制刷新
        deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        DeleteResponse deleteResponse = restClient.delete(deleteRequest, ElasticsearchConfig.COMMON_OPTIONS);
        Assert.isTrue(RestStatus.OK.equals(deleteResponse.status()));
    }

    /**
     * 批量插入
     */
    public void bulkInsert() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < 1000; i++) {
            TestData data = new TestData(i, "zhangsan" + i, 20);
            bulkRequest.add(new IndexRequest(TEST_INDEX)
                    .id(data.getId().toString())
                    .source(JSON.toJSONString(data), XContentType.JSON));
        }
        BulkResponse bulkResponse = restClient.bulk(bulkRequest, ElasticsearchConfig.COMMON_OPTIONS);
        if (bulkResponse.hasFailures()) {
            log.error("bulk index failed.{}", bulkResponse.buildFailureMessage());
        }
    }

    /**
     * ES游标存活时间（小时）
     */
    private static final long ES_SCROLL_ALIVE_TIME = 1L;

    /**
     * 游标滚动查询
     */
    public void scrollQuery() throws IOException {
        // 创建查询对象
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.query(QueryBuilders.termQuery("date", "2022-02-01"));
        searchBuilder.size(1000);
        // 创建查询请求对象
        SearchRequest searchRequest = new SearchRequest(TEST_INDEX);
        searchRequest.source(searchBuilder);
        searchRequest.scroll(TimeValue.timeValueHours(ES_SCROLL_ALIVE_TIME));
        // 第一次查询
        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
        // 游标编号
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        while (ArrayUtil.isNotEmpty(searchHits)) {
            for (SearchHit hit : searchHits) {
                final TestData testData = JSON.parseObject(hit.getSourceAsString(), TestData.class);
                // do something
            }
            // 设置游标点继续查询
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(TimeValue.timeValueHours(ES_SCROLL_ALIVE_TIME));
            searchResponse = restClient.scroll(scrollRequest, ElasticsearchConfig.COMMON_OPTIONS);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
        }
        // 一旦滚动条不再使用，显示清除滚动条
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        restClient.clearScroll(clearScrollRequest, ElasticsearchConfig.COMMON_OPTIONS);
    }

}
