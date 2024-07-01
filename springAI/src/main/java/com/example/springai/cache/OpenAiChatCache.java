package com.example.springai.cache;

import com.example.springai.entity.ChatOptions;
import com.example.springai.entity.ChatAiApi;
import com.example.springai.service.ApiService;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OpenAiChatCache {

    private String apiKey = "";
    private String baseUrl = "";
    private OpenAiApi cachedAiApi;
    private ChatOptions cachedChatOptions;
    private OpenAiChatClient cachedChatClient;

    private final ApiService apiService;

    public OpenAiChatCache(ApiService apiService) {
        this.apiService = apiService;
    }


    public OpenAiChatClient getChatClient() {
        if (cachedChatClient == null) {
            ChatOptions chatOptions = new ChatOptions();
            OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                    .withTemperature(chatOptions.getTemperature())
                    .withModel(chatOptions.getModel())
                    .build();

            cachedChatClient = new OpenAiChatClient(cachedAiApi, openAiChatOptions);

        }
        return cachedChatClient;
    }

    public OpenAiChatClient updateChatClient(ChatOptions newChatOptions) {

        OpenAiApi cachedChatAiApi = getCachedChatAiApi();
        if (cachedChatAiApi == null) {
            return null;
        }

        if (cachedChatClient == null || !newChatOptions.equals(cachedChatOptions)) {

            cachedAiApi = cachedChatAiApi;
            cachedChatOptions = newChatOptions;

            OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                    .withTemperature(newChatOptions.getTemperature())
                    .withModel(newChatOptions.getModel())
                    .build();

            cachedChatClient = new OpenAiChatClient(cachedAiApi, openAiChatOptions);
        }

        return cachedChatClient;
    }

    public OpenAiApi updateChatAiApi() {
//TODO 更新api，client也需要更新
        ChatAiApi chatAiApi = this.apiService.getChatAiApiByStatus(true);
        if (chatAiApi == null) {
            return null;
        }

        if (cachedAiApi == null
                || !Objects.equals(chatAiApi.getApiKey(), baseUrl)
                || !Objects.equals(chatAiApi.getBaseUrl(), apiKey)) {

            baseUrl = chatAiApi.getBaseUrl();
            apiKey = chatAiApi.getApiKey();
            cachedAiApi = new OpenAiApi(baseUrl, apiKey);
        }

        return cachedAiApi;
    }

    public OpenAiApi getCachedChatAiApi() {

        if (cachedAiApi == null) {

            ChatAiApi chatAiApi = this.apiService.getChatAiApiByStatus(true);
            if (chatAiApi == null) {
                return null;
            }

            baseUrl = chatAiApi.getBaseUrl();
            apiKey = chatAiApi.getApiKey();
            return new OpenAiApi(chatAiApi.getBaseUrl(), chatAiApi.getApiKey());
        }

        return cachedAiApi;
    }

}
