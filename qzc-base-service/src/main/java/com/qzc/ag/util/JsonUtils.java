package com.qzc.ag.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Object convert2Object(String jsonStr, Class<?> clazz){
        Object object = null;
        try {
            object = MAPPER.readValue(jsonStr, clazz);
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
        return object;
    }

    public static Map convert2Map(String jsonStr){
        Map map = null;
        try {
            map = MAPPER.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }

        return map;
    }

    public static String getValueFromJson(String jsonStr, String key){
        String result = null;
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonStr);
            result = jsonNode.get(key).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convert2Json(Object object){
        String jsonStr = null;
        try {
            jsonStr = MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonStr;
    }
}
