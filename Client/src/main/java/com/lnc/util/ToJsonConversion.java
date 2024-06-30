package com.lnc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToJsonConversion {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String codeLoginCredentials(String employeeID, String password) throws JsonProcessingException {
        Map<String,String> loginCredentials = new HashMap<>();
        loginCredentials.put("employeeID", employeeID);
        loginCredentials.put("password",  password);
        String jsonData = objectMapper.writeValueAsString(loginCredentials);

        String apiPath = "/login";

        return apiPath+ "&" + jsonData;
    }

    public String codeUserDetails(Employee employee) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(employee);

        String apiPath = "/register";

        return apiPath+ "&" + jsonData;
    }

    public String codeUserPreference(EmployeeProfile employeeProfile, String apiPath) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(employeeProfile);

        return apiPath+ "&" + jsonData;
    }

    public String codeMenuItem(MenuItem item, String apiPath) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(item);

        return apiPath+ "&" + jsonData;
    }

    public String codeEmployeeFeedback(Feedback feedback) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(feedback);

        String apiPath = "/employee/feedback";

        return apiPath+ "&" + jsonData;
    }

    public String codeItemName(String itemName, String apiPath) throws JsonProcessingException {
        Map<String,String> itemNameMap = new HashMap<>();
        itemNameMap.put("itemName", itemName);
        String jsonData = objectMapper.writeValueAsString(itemNameMap);

        return apiPath+ "&" + jsonData;
    }

    public String codeDailyMenu(DailyMenu menu) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(menu);

        String apiPath = "/chef/rolloutMenu";

        return apiPath+ "&" + jsonData;
    }

    public String codeMonthYear(String month, String year) throws JsonProcessingException {
        Map<String,String> monthYear = new HashMap<>();
        monthYear.put("month", month);
        monthYear.put("year",  year);
        String jsonData = objectMapper.writeValueAsString(monthYear);

        String apiPath = "/chef/generateReport";

        return apiPath+ "&" + jsonData;
    }

    public String codeEmployeeID(String employeeID) throws JsonProcessingException {
        Map<String,String> employeeMap = new HashMap<>();
        employeeMap.put("employee_id", employeeID);

        return objectMapper.writeValueAsString(employeeMap);
    }

    public String codeVotedItems(Map<String, Object> employeeVotingData) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(employeeVotingData);
        String apiPath = "/employee/vote";
        return apiPath+ "&" + jsonData;
    }

    public String codeItemsCount(int itemCount) throws JsonProcessingException {
        Map<String, Integer> itemNameMap = new HashMap<>();
        itemNameMap.put("itemCount", itemCount);
        String jsonData = objectMapper.writeValueAsString(itemNameMap);

        String apiPath = "/chef/getRecommendation";

        return apiPath+ "&" + jsonData;
    }

    public String codeQuestions(List<String> questions, String itemName) throws JsonProcessingException {
        Map<String, Object> questionsMap = new HashMap<>();
        questionsMap.put("questions", questions);
        questionsMap.put("itemName", itemName);

        String jsonData = objectMapper.writeValueAsString(questionsMap);
        String apiPath = "/discardItem/addQuestions";

        return apiPath + "&" + jsonData;
    }

    public String codeAnswers(List<Map<Integer, String>> answers, String employeeId) throws JsonProcessingException {
        Map<String, Object> answersMap = new HashMap<>();
        answersMap.put("answers", answers);
        answersMap.put("employeeId", employeeId);

        String jsonData = objectMapper.writeValueAsString(answersMap);
        String apiPath = "/discardItem/submitAnswers";

        return apiPath + "&" + jsonData;
    }
}
