package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

@Log4j
public class JSONUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String ToJson(Object value) {
        try {
            if (value == null) {
                return null;
            }
            return objectMapper.writeValueAsString(value);
        } catch (Exception var4) {
            log.error("JSONUtil.resourceToBean.error", var4);
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            try {
                return objectMapper.readValue(json, clazz);
            } catch (JsonProcessingException var4) {
                log.error("JSONUtil.parseSnakeObject.error", var4);
                return null;
            }
        }
    }

}
