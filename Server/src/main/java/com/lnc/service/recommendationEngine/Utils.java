package com.lnc.service.recommendationEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static void convertDecimalToDouble(List<Map<String, Object>> data, String key) {
        for (Map<String, Object> row : data) {
            row.put(key, ((Number) row.get(key)).doubleValue());
        }
    }

    public static String getPartOfMonth(int day) {
        if (1 <= day && day <= 10) {
            return "early";
        } else if (11 <= day && day <= 20) {
            return "mid";
        } else {
            return "late";
        }
    }

    public static String categorizePrice(double price, double lowPrice, double highPrice) {
        if (price <= lowPrice) {
            return "low";
        } else if (price <= highPrice) {
            return "mid";
        } else {
            return "high";
        }
    }

    public static double calculateCompositeScore(Map<String, Object> row) {
        return 0.2 * (Double) row.get("norm_avg_weekly_rating") +
                0.2 * (Double) row.get("norm_avg_monthly_rating") +
                0.15 * (Double) row.get("norm_avg_overall_rating") +
                0.1 * (Double) row.get("norm_total_votes") +
                0.1 * (Double) row.get("norm_avg_rating") +
                0.1 * (Double) row.get("norm_total_day_votes") +
                0.15 * (Double) row.get("norm_days_since_last_rollout");
    }

    public static double adjustCompositeScore(Map<String, Object> row, String partOfMonth) {
        String priceCategory = (String) row.get("price_category");
        double compositeScore = (Double) row.get("composite_score");

        if ("early".equals(partOfMonth) && "high".equals(priceCategory)) {
            return compositeScore * 1.2;
        } else if ("mid".equals(partOfMonth) && "mid".equals(priceCategory)) {
            return compositeScore * 1.2;
        } else if ("late".equals(partOfMonth) && "low".equals(priceCategory)) {
            return compositeScore * 1.2;
        } else {
            return compositeScore;
        }
    }

    public static List<Map<String, Object>> selectTopItems(List<Map<String, Object>> data, Object... categories) {
        Map<String, Integer> limits = new HashMap<>();
        for (int i = 0; i < categories.length; i += 2) {
            limits.put((String) categories[i], (Integer) categories[i + 1]);
        }

        Map<String, List<Map<String, Object>>> categorized = new HashMap<>();
        for (Map<String, Object> row : data) {
            String category = (String) row.get("category");
            if (!categorized.containsKey(category)) {
                categorized.put(category, new ArrayList<>());
            }
            if (categorized.get(category).size() < limits.getOrDefault(category, 0)) {
                categorized.get(category).add(row);
            }
        }

        List<Map<String, Object>> recommendations = new ArrayList<>();
        for (List<Map<String, Object>> categoryList : categorized.values()) {
            recommendations.addAll(categoryList);
        }

        return recommendations;
    }
}
