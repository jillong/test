package com.example.springai.controller;

import com.example.springai.cache.OpenAiChatClientHolder;
import com.example.springai.entity.ChatOptions;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setting")
public class SettingController {


    @Autowired
    private OpenAiChatClientHolder openAiChatClientHolder;

    @PostMapping
    public String updateModelSettings(@RequestBody ChatOptions options) {
        this.openAiChatClientHolder.updateOpenAiChatOptions(options);
        this.openAiChatClientHolder.updateChatClient(
                new OpenAiChatClient(
                        this.openAiChatClientHolder.getCachedChatAiApi(), this.openAiChatClientHolder.getOpenAiChatOptions()));
        return "Settings and API updated, new client created.";
    }
}
