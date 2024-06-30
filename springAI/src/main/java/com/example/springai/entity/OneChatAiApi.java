package com.example.springai.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class OneChatAiApi {

    private String id;

    /**
     * 是否使用
     */
    private Boolean status;

    private String baseUrl;

    private String apiKey;
    /**
     * 描述 可为空
     */
    private String describe;

    /**
     * 创建时间
     */
    private ZonedDateTime createTime;

    /**
     * 更新时间
     */
    private ZonedDateTime updateTime;
}
