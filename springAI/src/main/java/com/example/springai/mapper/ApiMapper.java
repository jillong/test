package com.example.springai.mapper;

import com.example.springai.entity.OneChatAiApi;

import java.util.List;

public interface ApiMapper {
    OneChatAiApi getApi(String id);

    List<OneChatAiApi> getApis();

    void createApi(OneChatAiApi oneChatAiApi);

    void updateApi(OneChatAiApi oneChatAiApi);

    void deleteApi(String id);

}
