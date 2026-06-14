package com.quanxiaoha.weblog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 *  将 Java 对象序列化为 JSON 字符串
 */
@Slf4j
public class JsonUtil {
    // ObjectMapper 的核心功能：序列化、反序列化、数据绑定、树模型、流式 API
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    public static String toJsonString(Object obj) {
        try {
            return INSTANCE.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }
}
