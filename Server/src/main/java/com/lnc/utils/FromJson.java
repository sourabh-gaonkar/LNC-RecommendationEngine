package com.lnc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.model.Feedback;
import com.lnc.model.MenuItem;

public class FromJson {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String getJsonValue(String key, String jsonData) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonData);

        return jsonNode.get(key).asText();
    }

    public MenuItem decodeMenuItem(String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, MenuItem.class);
    }

    public Feedback decodeFeedback(String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, Feedback.class);
    }
}
