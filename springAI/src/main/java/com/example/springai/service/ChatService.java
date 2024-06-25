package com.example.springai.service;

import com.example.springai.entity.dto.ChatDTO;

public interface ChatService {
    String getModelResponse(ChatDTO chatDTO);
}
