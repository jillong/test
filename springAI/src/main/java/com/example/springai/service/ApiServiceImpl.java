package com.example.springai.service;

import com.example.springai.entity.OneChatAiApi;
import com.example.springai.mapper.ApiMapper;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final ApiMapper apiMapper;

    public ApiServiceImpl(ApiMapper apiMapper) {
        this.apiMapper = apiMapper;
    }

    @Override
    public OneChatAiApi getChatAiApi(String id) {
        return this.apiMapper.getApi(id);
    }

    @Override
    public OneChatAiApi getChatAiApiByStatus(Boolean status) {
        return null;
    }

    @Override
    public List<OneChatAiApi> getChatAiApis() {
        return this.apiMapper.getApis();
    }

    @Override
    public void createChatAiApi(OneChatAiApi oneChatAiApi) {
        ZonedDateTime now = ZonedDateTime.now();
        oneChatAiApi.setCreateTime(now);
        oneChatAiApi.setUpdateTime(now);
        this.apiMapper.createApi(oneChatAiApi);
    }

    @Override
    public void updateChatAiApi(OneChatAiApi oneChatAiApi) {
        oneChatAiApi.setUpdateTime(ZonedDateTime.now());
        this.apiMapper.updateApi(oneChatAiApi);
    }

    @Override
    public void updateChatAiApis(List<OneChatAiApi> oneChatAiApis) {

    }

    @Override
    public void deleteChatAiApi(String id) {
        this.apiMapper.deleteApi(id);
    }
}
