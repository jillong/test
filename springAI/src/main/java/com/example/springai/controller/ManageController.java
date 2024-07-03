package com.example.springai.controller;

import com.example.springai.cache.OpenAiChatClientHolder;
import com.example.springai.entity.ChatAiApi;
import com.example.springai.service.ApiService;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/api")
public class ManageController {

    private final OpenAiChatClientHolder openAiChatClientHolder;

    private final ApiService apiService;

    public ManageController(OpenAiChatClientHolder openAiChatClientHolder, ApiService apiService) {
        this.openAiChatClientHolder = openAiChatClientHolder;
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
        if (this.openAiChatClientHolder.updateChatAiApi()) {

            this.openAiChatClientHolder.updateChatClient(
                    new OpenAiChatClient(
                            this.openAiChatClientHolder.getCachedChatAiApi(),
                            this.openAiChatClientHolder.getOpenAiChatOptions()));
        }
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

        if (this.openAiChatClientHolder.updateChatAiApi()) {

            this.openAiChatClientHolder.updateChatClient(
                    new OpenAiChatClient(
                            this.openAiChatClientHolder.getCachedChatAiApi(),
                            this.openAiChatClientHolder.getOpenAiChatOptions()));
        }
    }

    @DeleteMapping
    public void deleteChatAiApi(@RequestParam String id) {
        this.apiService.deleteChatAiApi(id);
        if (this.openAiChatClientHolder.updateChatAiApi()) {

            this.openAiChatClientHolder.updateChatClient(
                    new OpenAiChatClient(
                            this.openAiChatClientHolder.getCachedChatAiApi(),
                            this.openAiChatClientHolder.getOpenAiChatOptions()));
        }
    }

}
