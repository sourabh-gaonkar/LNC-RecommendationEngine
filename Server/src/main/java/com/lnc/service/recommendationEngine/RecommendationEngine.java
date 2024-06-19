package com.lnc.service.recommendationEngine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.RecommendationEngineQueries;
import com.lnc.utils.ConversionToJson;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationEngine {
    public String runEngine() throws SQLException, JsonProcessingException {
        RecommendationEngineQueries engineData = new RecommendationEngineQueries();
        Map<String, List<Map<String, Object>>> dataFrames = engineData.getAllData();

        List<Map<String, Object>> recommendations = processDataFrames(dataFrames);
        ConversionToJson toJson = new ConversionToJson();
        return toJson.codeEngineResponse(recommendations);
    }

    private List<Map<String, Object>> processDataFrames(Map<String, List<Map<String, Object>>> dataFrames) {
        List<Map<String, Object>> weeklyRatings = dataFrames.get("weekly_ratings");
        List<Map<String, Object>> monthlyRatings = dataFrames.get("monthly_ratings");
        List<Map<String, Object>> overallRatings = dataFrames.get("overall_ratings");
        List<Map<String, Object>> votes = dataFrames.get("votes");
        List<Map<String, Object>> prices = dataFrames.get("prices");
        List<Map<String, Object>> dayOfWeekRatings = dataFrames.get("day_of_week_ratings");
        List<Map<String, Object>> lastRollout = dataFrames.get("last_rollout");
        List<Map<String, Object>> dayOfWeekVotes = dataFrames.get("day_of_week_votes");

        // Convert decimal values to double
        convertDecimalToDouble(weeklyRatings, "avg_weekly_rating");
        convertDecimalToDouble(monthlyRatings, "avg_monthly_rating");
        convertDecimalToDouble(overallRatings, "avg_overall_rating");
        convertDecimalToDouble(votes, "total_votes");
        convertDecimalToDouble(prices, "price");
        convertDecimalToDouble(dayOfWeekRatings, "avg_rating");
        lastRollout.forEach(row -> row.put("last_rollout_date", ((Date) row.get("last_rollout_date")).toLocalDate()));
        convertDecimalToDouble(dayOfWeekVotes, "total_day_votes");

        // Get current day of week
        int currentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        // Calculate days since last rollout
        lastRollout.forEach(row -> row.put("days_since_last_rollout",
                ChronoUnit.DAYS.between((LocalDate) row.get("last_rollout_date"), LocalDate.now())));

        // Normalize days since last rollout
        double maxDaysSinceLastRollout = lastRollout.stream()
                .mapToDouble(row -> (Long) row.get("days_since_last_rollout"))
                .max()
                .orElse(1);

        lastRollout.forEach(row -> row.put("norm_days_since_last_rollout",
                (double) (Long) row.get("days_since_last_rollout") / maxDaysSinceLastRollout));

        // Filter day of week ratings and votes for the current day
        List<Map<String, Object>> currentDayRatings = dayOfWeekRatings.stream()
                .filter(row -> (Integer) row.get("day_of_week") == currentDayOfWeek)
                .collect(Collectors.toList());

        List<Map<String, Object>> currentDayVotes = dayOfWeekVotes.stream()
                .filter(row -> (Integer) row.get("day_of_week") == currentDayOfWeek)
                .collect(Collectors.toList());

        // Merge all data into a single list of maps
        Map<String, Map<String, Object>> data = mergeData(
                Arrays.asList(weeklyRatings, monthlyRatings, overallRatings, votes, prices, currentDayRatings, currentDayVotes, lastRollout)
        );

        // Fill NaN values with 0 for day-specific ratings and votes, and normalized days since last rollout
        data.values().forEach(row -> {
            row.putIfAbsent("avg_rating", 0.0);
            row.putIfAbsent("total_day_votes", 0.0);
            row.putIfAbsent("norm_days_since_last_rollout", 0.0);
        });

        // Normalize ratings and votes
        normalizeValues(data, "avg_weekly_rating");
        normalizeValues(data, "avg_monthly_rating");
        normalizeValues(data, "avg_overall_rating");
        normalizeValues(data, "total_votes");
        normalizeValues(data, "avg_rating");
        normalizeValues(data, "total_day_votes");

        // Determine the part of the month
        int currentDayOfMonth = LocalDate.now().getDayOfMonth();
        String partOfMonth = getPartOfMonth(currentDayOfMonth);

        // Calculate price categories
        List<Double> priceList = prices.stream().map(row -> (Double) row.get("price")).sorted().collect(Collectors.toList());
        double lowPrice = priceList.get((int) (priceList.size() * 0.33));
        double highPrice = priceList.get((int) (priceList.size() * 0.66));

        // Add a column for price category
        data.values().forEach(row -> row.put("price_category", categorizePrice((Double) row.get("price"), lowPrice, highPrice)));

        // Calculate the initial composite score
        data.values().forEach(row -> row.put("composite_score", calculateCompositeScore(row)));

        // Adjust composite score based on the part of the month
        data.values().forEach(row -> row.put("composite_score", adjustCompositeScore(row, partOfMonth)));

        // Sort by category and composite score
        List<Map<String, Object>> recommendations = data.values().stream()
                .sorted(Comparator.comparing((Map<String, Object> row) -> (String) row.get("category"))
                        .thenComparing(row -> -(Double) row.get("composite_score")))
                .collect(Collectors.toList());

        // Select top items for each category
        return selectTopItems(recommendations, "BREAKFAST", 3, "LUNCH", 4, "SNACK", 4, "DINNER", 4);
    }

    private void convertDecimalToDouble(List<Map<String, Object>> data, String key) {
        data.forEach(row -> row.put(key, ((Number) row.get(key)).doubleValue()));
    }

    private Map<String, Map<String, Object>> mergeData(List<List<Map<String, Object>>> dataFrames) {
        Map<String, Map<String, Object>> mergedData = new HashMap<>();

        for (List<Map<String, Object>> dataFrame : dataFrames) {
            for (Map<String, Object> row : dataFrame) {
                String itemId = row.get("item_id").toString();
                mergedData.putIfAbsent(itemId, new HashMap<>());
                mergedData.get(itemId).putAll(row);
            }
        }
        return mergedData;
    }

    private void normalizeValues(Map<String, Map<String, Object>> data, String key) {
        double maxValue = data.values().stream()
                .filter(row -> row.get(key) != null) // Filter out rows with null values for the key
                .mapToDouble(row -> (Double) row.get(key))
                .max()
                .orElse(1);

        data.values().forEach(row -> row.put("norm_" + key, row.get(key) != null ? (Double) row.get(key) / maxValue : 0.0));
    }

    private String getPartOfMonth(int day) {
        if (1 <= day && day <= 10) {
            return "early";
        } else if (11 <= day && day <= 20) {
            return "mid";
        } else {
            return "late";
        }
    }

    private String categorizePrice(double price, double lowPrice, double highPrice) {
        if (price <= lowPrice) {
            return "low";
        } else if (price <= highPrice) {
            return "mid";
        } else {
            return "high";
        }
    }

    private double calculateCompositeScore(Map<String, Object> row) {
        return 0.2 * (Double) row.get("norm_avg_weekly_rating") +
                0.2 * (Double) row.get("norm_avg_monthly_rating") +
                0.15 * (Double) row.get("norm_avg_overall_rating") +
                0.1 * (Double) row.get("norm_total_votes") +
                0.1 * (Double) row.get("norm_avg_rating") +
                0.1 * (Double) row.get("norm_total_day_votes") +
                0.15 * (Double) row.get("norm_days_since_last_rollout");
    }

    private double adjustCompositeScore(Map<String, Object> row, String partOfMonth) {
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

    private List<Map<String, Object>> selectTopItems(List<Map<String, Object>> data, Object... categories) {
        Map<String, Integer> limits = new HashMap<>();
        for (int i = 0; i < categories.length; i += 2) {
            limits.put((String) categories[i], (Integer) categories[i + 1]);
        }

        Map<String, List<Map<String, Object>>> categorized = new HashMap<>();
        for (Map<String, Object> row : data) {
            String category = (String) row.get("category");
            categorized.putIfAbsent(category, new ArrayList<>());
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
