package com.example.springai.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ChatOptions implements Serializable {

    /**
     * 大模型名称
     */
    private String model;

    /**
     * 是否流式回答
     */
    private boolean stream;


    /**
     * 是否使用增强模型
     */
    private boolean augmented;

    /**
     * 回答的随机性
     */
    private Float temperature;

    /**
     * 最大历史长度
     */
    private Integer maxHistoryLength;

}
