package com.example.springai.service;

import com.example.springai.entity.ChatAiApi;
import com.example.springai.mapper.ApiMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final ApiMapper apiMapper;

    public ApiServiceImpl(ApiMapper apiMapper) {
        this.apiMapper = apiMapper;
    }

    @Override
    public ChatAiApi getChatAiApi(String id) {
        return this.apiMapper.getApi(id);
    }

    @Override
    public ChatAiApi getChatAiApiByStatus(Boolean status) {
        return this.apiMapper.getChatAiApiByStatus(status);
    }

    @Override
    public List<ChatAiApi> getChatAiApis() {
        return this.apiMapper.getApis();
    }

    @Override
    public void createChatAiApi(ChatAiApi chatAiApi) {
        long currMillis = System.currentTimeMillis();
        chatAiApi.setCreateTime(new Date(currMillis));
        chatAiApi.setUpdateTime(new Date(currMillis));
        this.apiMapper.createApi(chatAiApi);
    }

    @Override
    public void updateChatAiApi(ChatAiApi chatAiApi) {
        chatAiApi.setUpdateTime(new Date(System.currentTimeMillis()));
        this.apiMapper.updateApi(chatAiApi);
    }

    @Override
    public void updateChatAiApis(List<ChatAiApi> chatAiApis) {
        Date date = new Date(System.currentTimeMillis());
        for (ChatAiApi chatAiApi : chatAiApis) {
            chatAiApi.setUpdateTime(date);
        }
        this.apiMapper.updateChatAiApis(chatAiApis);
    }

    @Override
    public void deleteChatAiApi(String id) {
        this.apiMapper.deleteApi(id);
    }
}
