package com.lnc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.model.DailyMenu;
import com.lnc.model.Employee;
import com.lnc.model.Feedback;
import com.lnc.model.MenuItem;
import java.util.HashMap;
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

    public String codeMenuItem(MenuItem item, String apiPath) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(item);

        return apiPath+ "&" + jsonData;
    }

    public String codeMenuItemName(String itemName) throws JsonProcessingException {
        Map<String,String> items = new HashMap<>();
        items.put("itemName", itemName);
        String jsonData = objectMapper.writeValueAsString(items);

        String apiPath = "/admin/deleteItem";

        return apiPath+ "&" + jsonData;
    }

    public String codeEmployeeFeedback(Feedback feedback) throws JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(feedback);

        String apiPath = "/employee/feedback";

        return apiPath+ "&" + jsonData;
    }

    public String codeItemName(String itemName) throws JsonProcessingException {
        Map<String,String> itemNameMap = new HashMap<>();
        itemNameMap.put("itemName", itemName);
        String jsonData = objectMapper.writeValueAsString(itemNameMap);

        String apiPath = "/chef/getFeedback";

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
}
