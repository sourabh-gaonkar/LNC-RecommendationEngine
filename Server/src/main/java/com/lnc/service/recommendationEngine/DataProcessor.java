package com.lnc.service.recommendationEngine;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DataProcessor {

    public List<Map<String, Object>> processDataFrames(Map<String, List<Map<String, Object>>> dataFrames, int itemCount) {
        List<Map<String, Object>> weeklyRatings = dataFrames.get("weekly_ratings");
        List<Map<String, Object>> monthlyRatings = dataFrames.get("monthly_ratings");
        List<Map<String, Object>> overallRatings = dataFrames.get("overall_ratings");
        List<Map<String, Object>> votes = dataFrames.get("votes");
        List<Map<String, Object>> prices = dataFrames.get("prices");
        List<Map<String, Object>> dayOfWeekRatings = dataFrames.get("day_of_week_ratings");
        List<Map<String, Object>> lastRollout = dataFrames.get("last_rollout");
        List<Map<String, Object>> dayOfWeekVotes = dataFrames.get("day_of_week_votes");

        Utils.convertDecimalToDouble(weeklyRatings, "avg_weekly_rating");
        Utils.convertDecimalToDouble(monthlyRatings, "avg_monthly_rating");
        Utils.convertDecimalToDouble(overallRatings, "avg_overall_rating");
        Utils.convertDecimalToDouble(votes, "total_votes");
        Utils.convertDecimalToDouble(prices, "price");
        Utils.convertDecimalToDouble(dayOfWeekRatings, "avg_rating");
        Utils.convertDecimalToDouble(dayOfWeekVotes, "total_day_votes");

        for (Map<String, Object> row : lastRollout) {
            row.put("last_rollout_date", ((Date) row.get("last_rollout_date")).toLocalDate());
        }

        int currentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        for (Map<String, Object> row : lastRollout) {
            row.put("days_since_last_rollout",
                    ChronoUnit.DAYS.between((LocalDate) row.get("last_rollout_date"), LocalDate.now()));
        }

        double maxDaysSinceLastRollout = 1;
        for (Map<String, Object> row : lastRollout) {
            maxDaysSinceLastRollout = Math.max(maxDaysSinceLastRollout, (Long) row.get("days_since_last_rollout"));
        }

        for (Map<String, Object> row : lastRollout) {
            row.put("norm_days_since_last_rollout",
                    (double) (Long) row.get("days_since_last_rollout") / maxDaysSinceLastRollout);
        }

        List<Map<String, Object>> currentDayRatings = new ArrayList<>();
        for (Map<String, Object> row : dayOfWeekRatings) {
            if ((Integer) row.get("day_of_week") == currentDayOfWeek) {
                currentDayRatings.add(row);
            }
        }

        List<Map<String, Object>> currentDayVotes = new ArrayList<>();
        for (Map<String, Object> row : dayOfWeekVotes) {
            if ((Integer) row.get("day_of_week") == currentDayOfWeek) {
                currentDayVotes.add(row);
            }
        }

        Map<String, Map<String, Object>> data = mergeData(
                Arrays.asList(weeklyRatings, monthlyRatings, overallRatings, votes, prices, currentDayRatings, currentDayVotes, lastRollout)
        );

        for (Map<String, Object> row : data.values()) {
            row.putIfAbsent("avg_rating", 0.0);
            row.putIfAbsent("total_day_votes", 0.0);
            row.putIfAbsent("norm_days_since_last_rollout", 0.0);
        }

        Normalizer.normalizeValues(data, "avg_weekly_rating");
        Normalizer.normalizeValues(data, "avg_monthly_rating");
        Normalizer.normalizeValues(data, "avg_overall_rating");
        Normalizer.normalizeValues(data, "total_votes");
        Normalizer.normalizeValues(data, "avg_rating");
        Normalizer.normalizeValues(data, "total_day_votes");

        int currentDayOfMonth = LocalDate.now().getDayOfMonth();
        String partOfMonth = Utils.getPartOfMonth(currentDayOfMonth);

        List<Double> priceList = new ArrayList<>();
        for (Map<String, Object> row : prices) {
            priceList.add((Double) row.get("price"));
        }
        Collections.sort(priceList);

        double lowPrice = priceList.get((int) (priceList.size() * 0.33));
        double highPrice = priceList.get((int) (priceList.size() * 0.66));

        for (Map<String, Object> row : data.values()) {
            row.put("price_category", Utils.categorizePrice((Double) row.get("price"), lowPrice, highPrice));
        }

        for (Map<String, Object> row : data.values()) {
            row.put("composite_score", Utils.calculateCompositeScore(row));
        }

        for (Map<String, Object> row : data.values()) {
            row.put("composite_score", Utils.adjustCompositeScore(row, partOfMonth));
        }

        List<Map<String, Object>> recommendations = new ArrayList<>(data.values());

        recommendations.sort(new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> row1, Map<String, Object> row2) {
                int categoryComparison = ((String) row1.get("category")).compareTo((String) row2.get("category"));
                if (categoryComparison == 0) {
                    return -Double.compare((Double) row1.get("composite_score"), (Double) row2.get("composite_score"));
                }
                return categoryComparison;
            }
        });

        return Utils.selectTopItems(recommendations, "BREAKFAST", itemCount, "LUNCH", itemCount, "SNACK", itemCount, "DINNER", itemCount);
    }

    private Map<String, Map<String, Object>> mergeData(List<List<Map<String, Object>>> dataFrames) {
        Map<String, Map<String, Object>> mergedData = new HashMap<>();

        for (List<Map<String, Object>> dataFrame : dataFrames) {
            for (Map<String, Object> row : dataFrame) {
                String itemId = row.get("item_id").toString();
                if (!mergedData.containsKey(itemId)) {
                    mergedData.put(itemId, new HashMap<>(row));
                } else {
                    mergedData.get(itemId).putAll(row);
                }
            }
        }
        return mergedData;
    }
}

