package com.example.springai.cache;

import com.example.springai.entity.ChatAiApi;
import com.example.springai.entity.ChatOptions;
import com.example.springai.service.ApiService;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OpenAiChatClientHolder {

    private String apiKey = "";
    private String baseUrl = "";
    private OpenAiApi openAiApi;
    private OpenAiChatClient openAiChatClient;
    private OpenAiChatOptions openAiChatOptions;

    private final ApiService apiService;

    public OpenAiChatClientHolder(ApiService apiService) {
        this.apiService = apiService;
    }

    public OpenAiChatClient getChatClient() {
        if (this.openAiChatClient == null) {
            this.openAiChatClient = new OpenAiChatClient(getCachedChatAiApi(), getOpenAiChatOptions());
        }
        return openAiChatClient;
    }

    public void updateChatClient(OpenAiChatClient openAiChatClient) {
        this.openAiChatClient = openAiChatClient;
    }

    public void updateOpenAiChatOptions(ChatOptions newChatOptions) {
        this.openAiChatOptions = OpenAiChatOptions.builder()
                .withTemperature(newChatOptions.getTemperature())
                .withModel(newChatOptions.getModel())
                .build();
    }

    public OpenAiChatOptions getOpenAiChatOptions() {
        if (this.openAiChatOptions == null) {
            this.openAiChatOptions = OpenAiChatOptions.builder().withModel("gpt-3.5-turbo").build();
        }
        return this.openAiChatOptions;
    }

    public boolean updateChatAiApi() {
        ChatAiApi chatAiApi = this.apiService.getChatAiApiByStatus(true);
        if (chatAiApi == null) {
            throw new RuntimeException("认证异常，没有可用的api key");
        }

        if (openAiApi == null
                || !Objects.equals(chatAiApi.getApiKey(), baseUrl)
                || !Objects.equals(chatAiApi.getBaseUrl(), apiKey)) {

            baseUrl = chatAiApi.getBaseUrl();
            apiKey = chatAiApi.getApiKey();
            this.openAiApi = new OpenAiApi(baseUrl, apiKey);
            return true;
        }

        return false;
    }

    public OpenAiApi getCachedChatAiApi() {

        if (openAiApi == null) {

            ChatAiApi chatAiApi = this.apiService.getChatAiApiByStatus(true);
            if (chatAiApi == null) {
                return null;
            }

            baseUrl = chatAiApi.getBaseUrl();
            apiKey = chatAiApi.getApiKey();
            return new OpenAiApi(chatAiApi.getBaseUrl(), chatAiApi.getApiKey());
        }

        return openAiApi;
    }

}
