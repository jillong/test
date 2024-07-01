package com.example.springai.controller;

import com.example.springai.cache.OpenAiChatCache;
import com.example.springai.entity.ChatAiApi;
import com.example.springai.service.ApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/api")
public class ManageController {

    private final OpenAiChatCache openAiChatCache;

    private final ApiService apiService;

    public ManageController(OpenAiChatCache openAiChatCache, ApiService apiService) {
        this.openAiChatCache = openAiChatCache;
        this.apiService = apiService;
    }

    @GetMapping
    public List<ChatAiApi> getChatAiApis() {
        return this.apiService.getChatAiApis();
    }

    @GetMapping("/id")
    public ChatAiApi getChatAiApi(@RequestParam String id) {
        return this.apiService.getChatAiApi(id);
    }

    @PostMapping
    public void createChatAiApi(@RequestBody ChatAiApi chatAiApi) {

        if (chatAiApi.getStatus()) {

            ChatAiApi chatAiApiByStatus = this.apiService.getChatAiApiByStatus(true);
            if (chatAiApiByStatus != null) {
                chatAiApiByStatus.setStatus(false);
                this.apiService.updateChatAiApi(chatAiApiByStatus);
            }

        }
        this.apiService.createChatAiApi(chatAiApi);
        this.openAiChatCache.updateChatAiApi();
    }

    @PutMapping
    public void updateChatAiApi(@RequestBody ChatAiApi chatAiApi) {
        ChatAiApi chatAiApiByStatus = this.apiService.getChatAiApiByStatus(true);
        if (chatAiApiByStatus != null) {
            chatAiApiByStatus.setStatus(false);
            this.apiService.updateChatAiApis(List.of(chatAiApiByStatus, chatAiApi));
        } else {
            this.apiService.updateChatAiApi(chatAiApi);
        }

        this.openAiChatCache.updateChatAiApi();
    }

    @DeleteMapping("/id")
    public void deleteChatAiApi(@RequestParam String id) {
        this.apiService.deleteChatAiApi(id);
        this.openAiChatCache.updateChatAiApi();
    }

}
