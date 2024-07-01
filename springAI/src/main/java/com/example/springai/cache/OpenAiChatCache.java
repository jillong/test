package com.example.springai.cache;

import com.example.springai.entity.ChatOptions;
import com.example.springai.entity.OneChatAiApi;
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
        OneChatAiApi oneChatAiApi = this.apiService.getChatAiApiByStatus(true);
        if (oneChatAiApi == null) {
            return null;
        }

        if (cachedAiApi == null
                || !Objects.equals(oneChatAiApi.getApiKey(), baseUrl)
                || !Objects.equals(oneChatAiApi.getBaseUrl(), apiKey)) {

            baseUrl = oneChatAiApi.getBaseUrl();
            apiKey = oneChatAiApi.getApiKey();
            cachedAiApi = new OpenAiApi(baseUrl, apiKey);
        }

        return cachedAiApi;
    }

    public OpenAiApi getCachedChatAiApi() {

        if (cachedAiApi == null) {

            OneChatAiApi oneChatAiApi = this.apiService.getChatAiApiByStatus(true);
            if (oneChatAiApi == null) {
                return null;
            }

            baseUrl = oneChatAiApi.getBaseUrl();
            apiKey = oneChatAiApi.getApiKey();
            return new OpenAiApi(oneChatAiApi.getBaseUrl(), oneChatAiApi.getApiKey());
        }

        return cachedAiApi;
    }

}
