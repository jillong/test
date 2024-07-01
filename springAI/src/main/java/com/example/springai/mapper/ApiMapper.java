package com.example.springai.mapper;

import com.example.springai.entity.ChatAiApi;

import java.util.List;

public interface ApiMapper {
    ChatAiApi getApi(String id);

    List<ChatAiApi> getApis();

    void createApi(ChatAiApi chatAiApi);

    void updateApi(ChatAiApi chatAiApi);

    void deleteApi(String id);

}
