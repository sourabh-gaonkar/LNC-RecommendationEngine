package com.lnc.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lnc.employee.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToJson {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String codeUserDetails(Employee employee) throws JsonProcessingException {
        return objectMapper.writeValueAsString(employee);
    }

    public String codeAllMenuItems(List<Map<String, Object>> menuList) throws JsonProcessingException {
        return objectMapper.writeValueAsString(menuList);
    }

//    public String codeAllFeedbacks(List<Map<String, Object>> feedbackList) throws JsonProcessingException {
//        return objectMapper.writeValueAsString(feedbackList);
//    }

    public String codeFeedbacks(List<Map<String, Object>> feedbackList, Map<String, Object> weeklyStat, Map<String, Object> overallStat) throws JsonProcessingException {
        ObjectNode rootNode = objectMapper.createObjectNode();

        ArrayNode feedbackArrayNode = objectMapper.createArrayNode();
        feedbackList.forEach(feedback -> feedbackArrayNode.add(objectMapper.valueToTree(feedback)));
        rootNode.set("feedback", feedbackArrayNode);

        rootNode.set("weeklyStat", objectMapper.valueToTree(weeklyStat));
        rootNode.set("overallStat", objectMapper.valueToTree(overallStat));

        return objectMapper.writeValueAsString(rootNode);
    }
}
