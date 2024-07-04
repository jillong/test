package com.example.springai.service;

import com.example.springai.cache.OpenAiChatClientHolder;
import com.example.springai.entity.Message;
import com.example.springai.entity.dto.ChatDTO;
import com.example.springai.utils.JacksonUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    String userPrompt = """
            给你提供一些数据参考: {info}，请回答我的问题：{query}。
            请你根据数据参考回复用户的请求。
            """;
    @Resource
    private OpenAiChatClientHolder openAiChatClientHolder;

    //@Resource
    private OpenAiChatClient chatClient;

    @Resource
    private VectorStore vectorStore;

    @Override
    public String getModelResponse(ChatDTO chatDTO) {
        chatClient = openAiChatClientHolder.getChatClient();
        String query = chatDTO.getQuery();
        if (StringUtils.isBlank(query)) {
            return "请输入内容";
        }

        boolean augmented = chatDTO.getChatOptions().isAugmented();
        List<Message> messages = chatDTO.getMessages();
        List<org.springframework.ai.chat.messages.Message> standardMessages = getStandardMessages(messages);

        if (augmented) {
            List<Document> documents = this.vectorStore.similaritySearch(query);
            if (CollectionUtils.isNotEmpty(documents)) {
                String info = documents.get(0).getContent();
                PromptTemplate promptTemplate = new PromptTemplate(userPrompt);
                standardMessages.add(promptTemplate.createMessage(Map.of("info", info, "query", query)));
            } else {
                augmented = false;
            }

        }

        if (!augmented) {
            standardMessages.add(new UserMessage(query));
        }

        List<Generation> response = chatClient.call(new Prompt(standardMessages)).getResults();
        StringBuilder result = new StringBuilder();
        for (Generation generation : response) {
            String content = generation.getOutput().getContent();
            result.append(content);
        }
        return JacksonUtil.ToJson(result.toString());
    }

    private List<org.springframework.ai.chat.messages.Message> getStandardMessages(List<Message> messages) {

        List<org.springframework.ai.chat.messages.Message> standardMessages = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(messages)) {
            return standardMessages;
        }

        for (Message message : messages) {
            String role = message.getRole();
            String content = message.getContent();

            MessageType messageType = MessageType.fromValue(role);
            switch (messageType) {
                case USER -> standardMessages.add(new UserMessage(content));
                case SYSTEM -> standardMessages.add(new SystemMessage(content));
                case FUNCTION -> standardMessages.add(new FunctionMessage(content));
                case ASSISTANT -> standardMessages.add(new AssistantMessage(content));
                default -> {
                }
            }

        }
        return standardMessages;
    }
}
