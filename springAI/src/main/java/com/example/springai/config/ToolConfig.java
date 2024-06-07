package com.example.springai.config;

import com.example.springai.service.PopulationService;
import com.example.springai.service.RecruitService;
import com.example.springai.service.WeatherService;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * @author jillong
 * @date 2024/6/7
 */

@Configuration
public class ToolConfig {

    @Bean
    @Description("获取当地的气温")
    public Function<WeatherService.Request, WeatherService.Response> currentWeather() {
        return new WeatherService();
    }

    @Bean
    @Description("获取当地的人口")
    public Function<PopulationService.Request, PopulationService.Response> currentPopulation() {
        return new PopulationService();
    }

    @Bean
    @Description("获取招聘职位")
    public Function<RecruitService.Request, RecruitService.Response> recruitPosition() {
        return new RecruitService();
    }

    /**
     * 生成向量库Bean
     *
     * @param embeddingClient 向量化客户端
     * @return 向量库
     */
    @Bean
    public VectorStore createVectorStore(EmbeddingClient embeddingClient) {
        return new SimpleVectorStore(embeddingClient);
    }

}
