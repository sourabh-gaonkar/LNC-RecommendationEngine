package com.lnc.service.recommendationEngine;

import java.util.Map;

public class Normalizer {

    public static void normalizeValues(Map<String, Map<String, Object>> data, String key) {
        double maxValue = 1;
        for (Map<String, Object> row : data.values()) {
            if (row.get(key) != null) {
                maxValue = Math.max(maxValue, (Double) row.get(key));
            }
        }

        for (Map<String, Object> row : data.values()) {
            row.put("norm_" + key, row.get(key) != null ? (Double) row.get(key) / maxValue : 0.0);
        }
    }
}

