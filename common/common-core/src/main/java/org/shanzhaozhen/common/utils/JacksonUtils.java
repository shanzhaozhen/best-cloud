package org.shanzhaozhen.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JacksonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("对象转Json失败：Object to Json error");
            throw new IllegalArgumentException("对象转Json失败：Object to Json error");
        }
    }

    public static <T> T stringToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Json转对象失败：Json to Object error");
            throw new IllegalArgumentException("Json转对象失败：Json to Object error");
        }
    }

    public static <T> List<T> stringToObjectList(String json, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Json转对象数组失败：Json to Object List error");
            throw new IllegalArgumentException("Json转对象数组失败：Json to Object List error");
        }
    }

}
