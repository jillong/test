package com.example.springai.service;

import com.example.springai.entity.ChatAiApi;

import java.util.List;

public interface ApiService {

    ChatAiApi getChatAiApi(String id);

    ChatAiApi getChatAiApiByStatus(Boolean status);

    List<ChatAiApi> getChatAiApis();

    void createChatAiApi(ChatAiApi chatAiApi);

    void updateChatAiApi(ChatAiApi chatAiApi);

    void updateChatAiApis(List<ChatAiApi> chatAiApis);

    void deleteChatAiApi(String id);
}
