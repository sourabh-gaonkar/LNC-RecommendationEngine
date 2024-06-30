package com.lnc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class FromJsonConversion {
  private final ObjectMapper objectMapper = new ObjectMapper();

  public String getJsonValue(String key, String jsonData) throws JsonProcessingException {
    JsonNode jsonNode = objectMapper.readTree(jsonData);

    return jsonNode.get(key).asText();
  }

  public List<Map<Integer, String>> decodeQuestions(String jsonData) throws JsonProcessingException {
    return objectMapper.readValue(jsonData, new TypeReference<List<Map<Integer, String>>>(){});
  }
}
