package com.example.springai.entity.dto;

import com.example.springai.entity.ChatOptions;
import com.example.springai.entity.Message;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ChatDTO implements Serializable {

    private ChatOptions chatOptions;

    /**
     * 历史消息
     */
    private List<Message> messages;

    /**
     * 用户当前问题
     */
    @NotNull
    private String query;


}
