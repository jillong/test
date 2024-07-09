package com.example.springai.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jillong
 * @date 2024/6/7
 */

public class JacksonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static String ObjectToJson(Object value) {

        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception ignored) {
        }

        return null;
    }

    public static <T> T StringToJson(String jsonString, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (Exception ignored) {
        }

        return null;
    }
}
