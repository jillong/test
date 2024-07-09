package com.example.springai.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 * @author jillong
 * @date 2024/6/7
 */

public class JacksonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(UUID.class, new ToStringSerializer());
        objectMapper.registerModule(module);
    }


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

    public static String listToJson(List<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    // 将 JSON 字符串转换为 List<String>
    public static List<String> jsonToList(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, listType);
    }


}
