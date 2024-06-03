package com.lnc.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.model.MenuItem;

import java.io.IOException;
import java.util.List;

public class JsonDataFormat {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public void prettyView(String jsonData) throws Exception {
        List<MenuItem> items;
        try {
            items = objectMapper.readValue(jsonData, new TypeReference<List<MenuItem>>() {});

        } catch (IOException ex) {
            throw new Exception("Error formatting JSON data.\n" + ex.getMessage());
        }

        System.out.printf("%-20s %-10s %-10s%n", "Item Name", "Price", "Availability");
        System.out.println("------------------------------------------------------");

        for (MenuItem item : items) {
            System.out.printf("%-20s %-10.2f %-10s%n", item.getItemName(), item.getPrice(), item.getAvailability() ? "Yes" : "No");
        }
    }
}
