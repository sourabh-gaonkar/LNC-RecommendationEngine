package com.lnc.service.recommendationEngine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    private List<Map<String, Object>> data;

    @BeforeEach
    public void setUp() {
        data = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("norm_avg_weekly_rating", 4.5);
        row1.put("norm_avg_monthly_rating", 4.7);
        row1.put("norm_avg_overall_rating", 4.8);
        row1.put("norm_total_votes", 100);
        row1.put("norm_avg_rating", 4.6);
        row1.put("norm_total_day_votes", 50);
        row1.put("norm_days_since_last_rollout", 30);
        row1.put("price_category", "mid");
        row1.put("composite_score", 0.0);
        row1.put("category", "BREAKFAST");
        data.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("norm_avg_weekly_rating", 3.5);
        row2.put("norm_avg_monthly_rating", 3.7);
        row2.put("norm_avg_overall_rating", 3.8);
        row2.put("norm_total_votes", 80);
        row2.put("norm_avg_rating", 3.6);
        row2.put("norm_total_day_votes", 30);
        row2.put("norm_days_since_last_rollout", 20);
        row2.put("price_category", "low");
        row2.put("composite_score", 0.0);
        row2.put("category", "LUNCH");
        data.add(row2);
    }

    @Test
    public void testConvertDecimalToDouble() {
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();
        row.put("price", 19.99);
        data.add(row);

        Utils.convertDecimalToDouble(data, "price");

        assertEquals(19.99, data.get(0).get("price"));
        assertTrue(data.get(0).get("price") instanceof Double);
    }

    @Test
    public void testGetPartOfMonth() {
        assertEquals("early", Utils.getPartOfMonth(5));
        assertEquals("mid", Utils.getPartOfMonth(15));
        assertEquals("late", Utils.getPartOfMonth(25));
    }

    @Test
    public void testCategorizePrice() {
        assertEquals("low", Utils.categorizePrice(50, 100, 200));
        assertEquals("mid", Utils.categorizePrice(150, 100, 200));
        assertEquals("high", Utils.categorizePrice(250, 100, 200));
    }

    @Test
    public void testAdjustCompositeScore() {
        Map<String, Object> row = data.get(0);
        row.put("composite_score", 10.0);
        double adjustedScore = Utils.adjustCompositeScore(row, "early");
        assertEquals(10.0, adjustedScore);

        row.put("price_category", "high");
        adjustedScore = Utils.adjustCompositeScore(row, "early");
        assertEquals(12.0, adjustedScore);
    }

    @Test
    public void testSelectTopItems() {
        List<Map<String, Object>> topItems = Utils.selectTopItems(data, "BREAKFAST", 1, "LUNCH", 1);
        assertEquals(2, topItems.size());
        assertEquals("BREAKFAST", topItems.get(0).get("category"));
        assertEquals("LUNCH", topItems.get(1).get("category"));

        topItems = Utils.selectTopItems(data, "BREAKFAST", 1);
        assertEquals(1, topItems.size());
        assertEquals("BREAKFAST", topItems.get(0).get("category"));
    }
}
