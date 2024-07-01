package com.example.springai.controller;

import com.example.springai.cache.OpenAiChatCache;
import com.example.springai.entity.OneChatAiApi;
import com.example.springai.service.ApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/api")
public class ApiController {

    private final OpenAiChatCache openAiChatCache;

    private final ApiService apiService;

    public ApiController(OpenAiChatCache openAiChatCache, ApiService apiService) {
        this.openAiChatCache = openAiChatCache;
        this.apiService = apiService;
    }

    @GetMapping
    public List<OneChatAiApi> getChatAiApis() {
        return this.apiService.getChatAiApis();
    }

    @GetMapping("/id")
    public OneChatAiApi getChatAiApi(@RequestParam String id) {
        return this.apiService.getChatAiApi(id);
    }

    @PostMapping
    public void createChatAiApi(@RequestBody OneChatAiApi oneChatAiApi) {

        if (oneChatAiApi.getStatus()) {

            OneChatAiApi chatAiApiByStatus = this.apiService.getChatAiApiByStatus(true);
            if (chatAiApiByStatus != null) {
                chatAiApiByStatus.setStatus(false);
                this.apiService.updateChatAiApi(chatAiApiByStatus);
            }

        }
        this.apiService.createChatAiApi(oneChatAiApi);
        this.openAiChatCache.updateChatAiApi();
    }

    @PutMapping
    public void updateChatAiApi(@RequestBody OneChatAiApi oneChatAiApi) {
        OneChatAiApi chatAiApiByStatus = this.apiService.getChatAiApiByStatus(true);
        if (chatAiApiByStatus != null) {
            chatAiApiByStatus.setStatus(false);
            this.apiService.updateChatAiApis(List.of(chatAiApiByStatus, oneChatAiApi));
        } else {
            this.apiService.updateChatAiApi(oneChatAiApi);
        }

        this.openAiChatCache.updateChatAiApi();
    }

    @DeleteMapping("/id")
    public void deleteChatAiApi(@RequestParam String id) {
        this.apiService.deleteChatAiApi(id);
        this.openAiChatCache.updateChatAiApi();
    }

}
