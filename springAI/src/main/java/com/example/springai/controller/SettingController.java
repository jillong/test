package com.example.springai.controller;

import com.example.springai.cache.OpenAiChatCache;
import com.example.springai.entity.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setting")
public class SettingController {


    @Autowired
    private OpenAiChatCache openAiChatCache;

    @PostMapping("/updateModelSettings")
    public String updateModelSettings(@RequestBody ChatOptions options) {
        this.openAiChatCache.updateChatClient(options);
        return "Settings and API updated, new client created.";
    }


    @PostMapping
    public void setModelOptions(@RequestBody ChatOptions chatOptions) {

    }
}
