package com.example.springai.controller;

import com.example.springai.utils.JacksonUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jillong
 * @date 2024/6/6
 */

@Log4j2
@RestController
@RequestMapping("/ai/vectorStore")
public class VectorController {

    @Resource
    private VectorStore vectorStore;

    @Resource
    private EmbeddingClient embeddingClient;

    /**
     * 向量化
     *
     * @param message 输入的文本
     * @return 向量化结果
     */
    @GetMapping("/embedding")
    public String embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        // 向量化
        EmbeddingResponse embeddingResponse = this.embeddingClient.embedForResponse(List.of(message));
        return JacksonUtil.ObjectToJson(embeddingResponse);
    }

    /**
     * 向量化数据添加到数据库
     *
     * @param content 文本内容
     * @return 添加结果
     */
    @GetMapping("/add")
    public String vectorStoreAdd(@RequestParam(value = "content") String content) {
        List<Document> documentList = new ArrayList<>();
        Document document = new Document(content);
        documentList.add(document);
        this.vectorStore.add(documentList);
        return JacksonUtil.ObjectToJson(document);
    }

    /**
     * 向量化数据搜索
     *
     * @param query 查询内容
     * @return 搜索结果
     */
    @GetMapping("/search")
    public String vectorStoreSearch(@RequestParam(value = "query") String query) {
        List<Document> documents = this.vectorStore.similaritySearch(query);
        return JacksonUtil.ObjectToJson(documents);
    }

}
