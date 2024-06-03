package com.lnc.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(menuList);
    }
}
