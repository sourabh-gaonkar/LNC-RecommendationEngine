package com.lnc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.model.MenuItem;
import com.lnc.model.MenuItemResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonDataFormat {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public void prettyView(String jsonData) throws Exception {
        List<MenuItemResponse> items;
        try {
            items = objectMapper.readValue(jsonData, new TypeReference<List<MenuItemResponse>>() {});
        } catch (IOException ex) {
            throw new Exception("Error formatting JSON data.\n" + ex.getMessage());
        }

        System.out.printf("%-30s %-10s %-15s %-10s%n", "Item Name", "Price", "Availability", "Category");
        System.out.println("--------------------------------------------------------------------------");

        for (MenuItemResponse item : items) {
            System.out.printf("%-30s %-10.2f %-15s %-10s%n",
                    item.getItemName(),
                    item.getPrice(),
                    item.isAvailable() ? "Yes" : "No",
                    item.getCategory());
        }
    }

    public void viewFormattedFeedbacks(String jsonData) throws JsonProcessingException {
        Map<String, Object> dataMap = objectMapper.readValue(jsonData, Map.class);

        Double weeklyRating = (Double) ((Map) dataMap.get("weeklyStat")).get("weekly_rating");
        Double overallRating = (Double) ((Map) dataMap.get("overallStat")).get("overall_rating");
        System.out.println("\nweekly_rating: " + weeklyRating + "\t\t\toverall_rating: " + overallRating + "\n");

        List<Map<String, Object>> feedbackList = (List<Map<String, Object>>) dataMap.get("feedback");
        System.out.printf("%-16s %-7s %-50s %s%n", "Employee Name", "Rating", "Comment", "Date");
        System.out.println("----------------------------------------------------------------------------------------");

        for (Map<String, Object> feedback : feedbackList) {
            System.out.printf("%-16s %-7d %-50s %s%n", feedback.get("user_name"), feedback.get("rating"), feedback.get("comment"), feedback.get("feedback_date"));
        }
    }
}
