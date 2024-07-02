package com.example.springai.mapper;

import com.example.springai.entity.ChatAiApi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApiMapper {
    ChatAiApi getApi(String id);

    List<ChatAiApi> getApis();

    ChatAiApi getChatAiApiByStatus(Boolean status);

    void createApi(ChatAiApi chatAiApi);

    void updateApi(ChatAiApi chatAiApi);

    void updateChatAiApis(@Param("chatAiApis") List<ChatAiApi> chatAiApis);

    void deleteApi(String id);

}
