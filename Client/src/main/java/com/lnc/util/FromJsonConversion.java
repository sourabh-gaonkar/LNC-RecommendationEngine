package com.lnc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FromJsonConversion {
  private final ObjectMapper objectMapper = new ObjectMapper();

  public String getJsonValue(String key, String jsonData) throws JsonProcessingException {
    JsonNode jsonNode = objectMapper.readTree(jsonData);

    return jsonNode.get(key).asText();
  }
}
