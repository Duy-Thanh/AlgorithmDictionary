package com.algorithmswiki.apps.backend.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static<T> T fromJSON(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
