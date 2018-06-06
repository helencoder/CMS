package com.helencoder.controller;

import com.google.gson.Gson;
import com.helencoder.entity.AbstractVo;
import com.helencoder.service.AbstractService;
import com.helencoder.service.SearchService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 搜索控制器
 *
 * Created by zhenghailun on 2018/6/5.
 */
@RestController
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Autowired
    private TransportClient client;

    @Autowired
    private AbstractService abstractService;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search/content")
    @ResponseBody
    public ResponseEntity searchContent(@RequestParam(name="content",defaultValue = "")String content, @RequestParam(name="count",defaultValue = "")String count) {

        if(content.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<AbstractVo> list = searchService.getListByContent(content, Integer.parseInt(count));
        for (AbstractVo vo : list) {
            System.out.println(vo.getId() + "\t" + vo.getAbs() + "\t" + vo.getContent());
        }

        if(list.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(list, HttpStatus.OK);
    }

    // 设置Mapping示例
    public ResponseEntity setMapping() throws Exception {
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        // 创建index
        indicesAdminClient.prepareCreate("corpus")
                .setSettings(Settings.builder()
                        .put("analysis.analyzer", "ik_max_word"))
                .get();

        // 设置Mapping
        XContentBuilder mapping = jsonBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("content")
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("abstract")
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                        .endObject()
                    .endObject()
                .endObject();

        client.admin().indices().preparePutMapping("corpus")
                .setType("abstract")
                .setSource(mapping)
                .get();

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/search/create")
    @ResponseBody
    public ResponseEntity create() {
        // mysql数据入ES

        String limit = "1000";
        for (int i = 0; i < 1000; i++) {
            int off = i * 1000;
            String offset = "" + off;
            List<AbstractVo> abstractList = abstractService.getAbstractList("", "", limit, offset);
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            for (AbstractVo abstractVo : abstractList) {
                String id = abstractVo.getId();

                Map<String, Object> doc = new HashMap<>();
                doc.put("content", abstractVo.getContent());
                doc.put("abstract", abstractVo.getAbs());

                bulkRequest.add(client.prepareIndex("corpus", "abstract", id)
                        .setSource(doc));
            }

            System.out.println("当前数据为：" + offset + "\t" + abstractList.size());
            bulkRequest.get();
        }


        return new ResponseEntity("success", HttpStatus.OK);
    }

}
