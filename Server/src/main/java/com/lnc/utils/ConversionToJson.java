package com.lnc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lnc.model.Employee;
import com.lnc.model.Notification;

import java.util.List;
import java.util.Map;

public class ConversionToJson {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String codeUserDetails(Employee employee) throws JsonProcessingException {
        return objectMapper.writeValueAsString(employee);
    }

    public String codeAllMenuItems(List<Map<String, Object>> menuList) throws JsonProcessingException {
        return objectMapper.writeValueAsString(menuList);
    }

    public String codeFeedbacks(List<Map<String, Object>> feedbackList, Map<String, Object> weeklyStat, Map<String, Object> overallStat) throws JsonProcessingException {
        ObjectNode rootNode = objectMapper.createObjectNode();

        ArrayNode feedbackArrayNode = objectMapper.createArrayNode();
        feedbackList.forEach(feedback -> feedbackArrayNode.add(objectMapper.valueToTree(feedback)));
        rootNode.set("feedback", feedbackArrayNode);

        rootNode.set("weeklyStat", objectMapper.valueToTree(weeklyStat));
        rootNode.set("overallStat", objectMapper.valueToTree(overallStat));

        return objectMapper.writeValueAsString(rootNode);
    }

    public String codeEngineResponse(List<Map<String, Object>> recommendations) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(recommendations);
    }

    public String codeMonthlyReport(List<Map<String, Object>> reportData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(reportData);
    }

    public String codeNotifications(List<Notification> notifications) throws JsonProcessingException {
        return objectMapper.writeValueAsString(notifications);
    }

    public String codeTodaysMenu(List<Map<String, Object>> todaysMenu) throws JsonProcessingException {
        return objectMapper.writeValueAsString(todaysMenu);
    }

    public String codeItemList(List<String> itemList) throws JsonProcessingException {
        return objectMapper.writeValueAsString(itemList);
    }

    public String codeQuestions(List<Map<Integer, String>> questions) throws JsonProcessingException {
        return objectMapper.writeValueAsString(questions);
    }

    public String codeQuestionAnswers(List<Map<String, List<String>>> questionAnswers) throws JsonProcessingException {
        return objectMapper.writeValueAsString(questionAnswers);
    }
}
