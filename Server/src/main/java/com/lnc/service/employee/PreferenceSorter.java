package com.lnc.service.employee;

import com.lnc.DB.EmployeeProfileQueries;
import com.lnc.DB.MenuItemProfileQueries;

import java.util.*;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PreferenceSorter {
    private final Logger logger = Logger.getLogger(PreferenceSorter.class.getName());
    private final EmployeeProfileQueries employeeProfileQueries = new EmployeeProfileQueries();
    private final MenuItemProfileQueries menuItemProfileQueries = new MenuItemProfileQueries();

    public List<Map<String, Object>> sortMenuItems(List<Map<String, Object>> menuItems, String employeeId) {
        Map<String, Object> employeePreferences = employeeProfileQueries.getEmployeePreferences(employeeId);

        // Group items by category
        Map<String, List<Map<String, Object>>> categorizedItems = new HashMap<>();
        for (Map<String, Object> item : menuItems) {
            String category = (String) item.get("category");
            categorizedItems.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
        }

        // Define category order
        List<String> categoryOrder = Arrays.asList("BREAKFAST", "LUNCH", "SNACK", "DINNER");

        // Sort each category based on preferences
        List<Map<String, Object>> sortedItems = new ArrayList<>();
        for (String category : categoryOrder) {
            List<Map<String, Object>> itemsInCategory = categorizedItems.getOrDefault(category, new ArrayList<>());
            itemsInCategory.sort((item1, item2) -> {
                Map<String, Object> profile1 = menuItemProfileQueries.getMenuItemProfile((String) item1.get("item_name"));
                Map<String, Object> profile2 = menuItemProfileQueries.getMenuItemProfile((String) item2.get("item_name"));
                return compareProfiles(profile1, profile2, employeePreferences);
            });
            sortedItems.addAll(itemsInCategory);
        }

        return sortedItems;
    }

    private int compareProfiles(Map<String, Object> profile1, Map<String, Object> profile2, Map<String, Object> preferences) {
        int result;

        // Compare diet preference with special handling for VEG, EGG, and NON-VEG
        result = compareDietPreference(profile1, profile2, preferences);
        if (result != 0) return result;

        // Compare regional preference
        result = comparePreference("region", profile1, profile2, preferences);
        if (result != 0) return result;

        // Compare spice level
        result = comparePreference("spice_level", profile1, profile2, preferences);
        if (result != 0) return result;

        // Compare sweetness
        result = comparePreference("sweetness", profile1, profile2, preferences);
        return result;
    }

    private int compareDietPreference(Map<String, Object> profile1, Map<String, Object> profile2, Map<String, Object> preferences) {
        String dietPreference = (String) preferences.get("diet_preference");
        String dietType1 = (String) profile1.get("diet_type");
        String dietType2 = (String) profile2.get("diet_type");

        switch (dietPreference) {
            case "VEG":
                if ("VEG".equals(dietType1) && !"VEG".equals(dietType2)) return -1;
                if (!"VEG".equals(dietType1) && "VEG".equals(dietType2)) return 1;
                if (!"VEG".equals(dietType1) && "EGG".equals(dietType2) || "NON-VEG".equals(dietType2)) return 1;
                if (!"VEG".equals(dietType2) && "EGG".equals(dietType1) || "NON-VEG".equals(dietType1)) return -1;
                break;
            case "EGG":
                if ("EGG".equals(dietType1) && !"EGG".equals(dietType2)) return -1;
                if (!"EGG".equals(dietType1) && "EGG".equals(dietType2)) return 1;
                if (!"EGG".equals(dietType1) && "VEG".equals(dietType2)) return 1;
                if (!"EGG".equals(dietType2) && "VEG".equals(dietType1)) return -1;
                if (!"EGG".equals(dietType1) && "NON-VEG".equals(dietType2)) return 1;
                if (!"EGG".equals(dietType2) && "NON-VEG".equals(dietType1)) return -1;
                break;
            case "NON-VEG":
                if ("NON-VEG".equals(dietType1) && !"NON-VEG".equals(dietType2)) return -1;
                if (!"NON-VEG".equals(dietType1) && "NON-VEG".equals(dietType2)) return 1;
                if (!"NON-VEG".equals(dietType1) && "EGG".equals(dietType2)) return 1;
                if (!"NON-VEG".equals(dietType2) && "EGG".equals(dietType1)) return -1;
                if (!"NON-VEG".equals(dietType1) && "VEG".equals(dietType2)) return 1;
                if (!"NON-VEG".equals(dietType2) && "VEG".equals(dietType1)) return -1;
                break;
        }
        return 0;
    }

    private int comparePreference(String key, Map<String, Object> profile1, Map<String, Object> profile2, Map<String, Object> preferences) {
        Object prefValue = preferences.get(key);
        Object value1 = profile1.get(key);
        Object value2 = profile2.get(key);

        if (value1.equals(prefValue) && !value2.equals(prefValue)) {
            return -1;
        } else if (!value1.equals(prefValue) && value2.equals(prefValue)) {
            return 1;
        }
        return 0;
    }
}
