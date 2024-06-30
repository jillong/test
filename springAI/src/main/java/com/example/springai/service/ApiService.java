package com.example.springai.service;

import com.example.springai.entity.OneChatAiApi;

import java.util.List;

public interface ApiService {

    OneChatAiApi getChatAiApi(String id);

    OneChatAiApi getChatAiApiByStatus(Boolean status);

    List<OneChatAiApi> getChatAiApis();

    void createChatAiApi(OneChatAiApi oneChatAiApi);

    void updateChatAiApi(OneChatAiApi oneChatAiApi);

    void updateChatAiApis(List<OneChatAiApi> oneChatAiApis);

    void deleteChatAiApi(String id);
}
