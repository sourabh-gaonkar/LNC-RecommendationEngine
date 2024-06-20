package com.lnc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.model.DailyMenu;
import com.lnc.model.Feedback;
import com.lnc.model.MenuItem;
import java.util.Map;

public class ConversionFromJson {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String getJsonValue(String key, String jsonData) throws JsonProcessingException, NullPointerException {
        JsonNode jsonNode = objectMapper.readTree(jsonData);
        return jsonNode.get(key).asText();
    }

    public MenuItem decodeMenuItem(String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, MenuItem.class);
    }

    public Feedback decodeFeedback(String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, Feedback.class);
    }

    public DailyMenu decodeDailyMenu(String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, DailyMenu.class);
    }

    public Map<String, Object> decodeVotedMenu(String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
    }
}
