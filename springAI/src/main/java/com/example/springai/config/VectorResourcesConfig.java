package com.example.springai.config;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author jillong
 * @date 2024/6/7
 */
@Configuration
public class VectorResourcesConfig {

    /**
     * 生成向量库Bean，存入内存
     *
     * @param embeddingClient 向量化客户端
     * @return 向量库
     */
    /*@Bean
    public VectorStore createVectorStore(EmbeddingClient embeddingClient) {
        return new SimpleVectorStore(embeddingClient);
    }*/

    /**
     * 生成向量库Bean，存入PG数据库
     *
     * @param jdbcTemplate    jdbc模板
     * @param embeddingClient 向量化客户端
     * @return pg向量库
     */
    @Bean
    public VectorStore createVectorStoreByPG(JdbcTemplate jdbcTemplate, EmbeddingClient embeddingClient) {
        return new PgVectorStore(jdbcTemplate, embeddingClient);
    }

}
