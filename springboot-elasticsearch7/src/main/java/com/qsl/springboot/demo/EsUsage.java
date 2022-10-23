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
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
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
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
        // 响应状态
        boolean acknowledged = createIndexResponse.isAcknowledged();
        log.info("create index {} response:{}", index, acknowledged);
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

    private TestData generateTestData() {
        TestData expectedData = new TestData();
        expectedData.setId(1001);
        expectedData.setName("张三");
        expectedData.setAge(20);
        return expectedData;
    }

    /**
     * 插入数据
     */
    private void insertData(String index, TestData data) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index);
        // 设置ID，不设置则默认生成UUID
        indexRequest.id(data.getId().toString());
        // 强制刷新数据
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        // 将数据转换为json格式
        indexRequest.source(JSON.toJSONString(data), XContentType.JSON);
        IndexResponse response = restClient.index(indexRequest, ElasticsearchConfig.COMMON_OPTIONS);
        Assert.isTrue(RestStatus.CREATED.equals(response.status()));
    }

    /**
     * 精确查询
     * 根据业务id查询
     */
    private void queryByBusinessId(String businessId) throws IOException {
        SearchRequest searchRequest = new SearchRequest(TEST_INDEX);
        searchRequest.source(new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("id", businessId))
        );
        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
        Assert.isTrue(RestStatus.OK.equals(searchResponse.status()));

        // 查询条数为1
        SearchHits hits = searchResponse.getHits();
        Assert.isTrue(1 == hits.getTotalHits().value);

        // 判断查询数据和插入数据是否相等
        String dataJson = hits.getHits()[0].getSourceAsString();
        Assert.isTrue(JSON.toJSONString(generateTestData()).equals(dataJson));
    }

    /**
     * 根据id查询数据
     */
    private void queryById(String id) throws IOException {
        GetRequest getRequest = new GetRequest();
        getRequest.index(TEST_INDEX).id(id);
        GetResponse getResponse = restClient.get(getRequest, ElasticsearchConfig.COMMON_OPTIONS);
        Assert.isTrue(getResponse.isExists());

        // 判断查询数据和插入数据是否相等
        String dataJson = getResponse.getSourceAsString();
        System.out.println(dataJson);
    }

    /**
     * 更新数据
     */
    public void update() throws IOException {
        // 先插入数据
        TestData data = new TestData(3, "测试数据03", 29);
        this.insertData(TEST_INDEX, data);

        // 更新数据
        data.setName("测试数据被更新");

        UpdateRequest updateRequest = new UpdateRequest(TEST_INDEX, data.getId().toString());
        updateRequest.doc(JSON.toJSONString(data), XContentType.JSON);
        // 强制刷新数据（也可以不需要）
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
        System.out.println(bulkResponse.getTook());
    }

    /**
     * 全量查询
     */
    public void queryAll(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(new SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery())
        );

        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
        Assert.isTrue(RestStatus.OK.equals(searchResponse.status()));
        System.out.println(searchResponse.getTook());

        final SearchHits hits = searchResponse.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 条件查询
     */
    public void queryByCondition(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("age", 30))
        );

        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
    }

    /**
     * 分页查询、排序、过滤字段
     */
    public void queryByPage(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        final SearchSourceBuilder searchBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("age", 30));
        // 排序
        searchBuilder.sort("age", SortOrder.DESC);
        // 分页
        searchBuilder.from(0).size(2);
        // 过滤字段
        String[] excludes = {"age"};
        String[] includes = {};
        searchBuilder.fetchSource(includes, excludes);
        searchRequest.source(searchBuilder);

        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
    }

    /**
     * 组合查询
     */
    public void queryByCombine(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "男"));
        searchSourceBuilder.query(boolQueryBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
    }

    /**
     * 范围查询
     */
    public void queryByRange(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        rangeQueryBuilder.gte(30);
        rangeQueryBuilder.lte(40);
        searchSourceBuilder.query(rangeQueryBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
    }

    /**
     * 模糊查询
     */
    public void queryByLike(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // fuzziness表示模糊多少个字符
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("name", "wang").fuzziness(Fuzziness.ONE));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
    }

    /**
     * 高亮查询
     */
    public void queryByHighLight(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        final TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan");
        searchSourceBuilder.query(termQueryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>").postTags("</font>");
        highlightBuilder.field("name");
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
    }

    /**
     * 聚合查询
     */
    public void queryByAgg(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 聚合查询
        AggregationBuilder aggregationBuilder1 = AggregationBuilders.max("maxAge").field("age");
        searchSourceBuilder.aggregation(aggregationBuilder1);
        // 分组查询
        AggregationBuilder aggregationBuilder2 = AggregationBuilders.terms("ageGroup").field("age");
        searchSourceBuilder.aggregation(aggregationBuilder2);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
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
