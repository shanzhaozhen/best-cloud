package org.shanzhaozhen.common.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JacksonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSONString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("对象转Json失败：Object to Json error");
            throw new IllegalArgumentException("对象转Json失败：Object to Json error");
        }
    }

    public static <T> T toOPojo(String json, Class<T> beanType) {
        try {
            return objectMapper.readValue(json, beanType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Json转对象失败：Json to Object error");
            throw new IllegalArgumentException("Json转对象失败：Json to Object error");
        }
    }

    public static <T> List<T> toPojoList(String json, Class<T> beanType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Json转对象数组失败：Json to Object List error");
            throw new IllegalArgumentException("Json转对象数组失败：Json to Object List error");
        }
    }

}
