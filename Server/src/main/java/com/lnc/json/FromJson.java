package com.lnc.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.admin.MenuItem;

public class FromJson {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String getJsonValue(String key, String jsonData) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonData);

        return jsonNode.get(key).asText();
    }

    public MenuItem decodeMenuItem(String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, MenuItem.class);
    }
}
