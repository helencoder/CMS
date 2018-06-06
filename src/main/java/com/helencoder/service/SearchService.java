package com.helencoder.service;

import com.helencoder.entity.AbstractVo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 搜索服务(ElasticSearch)
 *
 * Created by zhenghailun on 2018/6/5.
 */
@Service
public class SearchService {
    @Autowired
    private TransportClient client;

    public List<AbstractVo> getListByContent(String term, int count) {
        List<AbstractVo> list = new ArrayList<>();

        QueryBuilder mathcQuery = QueryBuilders
                .matchQuery("content", term)
                .operator(Operator.AND);

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("content")
                .preTags("<span style = \"color:red\" >")
                .preTags("</span>");

        SearchResponse response = client.prepareSearch("corpus")
                .setQuery(mathcQuery)
                .highlighter(highlightBuilder)
                .setSize(count)
                .get();

        SearchHits hits = response.getHits();

        System.out.println("共搜索到：" + hits.getTotalHits() + "条数据");
        for (SearchHit hit : hits) {
            String id = hit.getId();
            Map<String, Object> map = hit.getSourceAsMap();
            String abs = map.get("abstract").toString();
            String content = map.get("content").toString();
            String score = String.valueOf(hit.getScore());

            AbstractVo vo = new AbstractVo();
            vo.setId(id);
            vo.setContent(content);
            vo.setAbs(abs);
            vo.setSimilarity(score);
            list.add(vo);

        }

        return list;
    }

    public List<AbstractVo> getListByAbstract(String term) {
        List<AbstractVo> list = new ArrayList<>();

        return list;
    }
}
