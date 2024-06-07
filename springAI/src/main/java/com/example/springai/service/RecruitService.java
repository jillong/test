package com.example.springai.service;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.function.Function;


/**
 * @author jillong
 * @date 2024/6/7
 * 招聘投递服务
 */
public class RecruitService implements Function<RecruitService.Request, RecruitService.Response> {

    /**
     * Recruit Function request.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonClassDescription("Recruit API request")
    public record Request(@JsonProperty(required = true, value = "人名")
                          @JsonPropertyDescription("投递简历人姓名，例如: 刘磊") String name) {
    }


    /**
     * Recruit Function response.
     */
    public record Response(String position) {
    }

    @Override
    public RecruitService.Response apply(RecruitService.Request request) {
        String position = "未知";
        if (request.name().contains("刘磊")) {
            position = "算法工程师";
        }
        return new RecruitService.Response(position);
    }

}
