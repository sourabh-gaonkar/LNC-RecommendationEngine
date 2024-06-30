package com.lnc.service.sentimentAnalysis;

import java.util.*;

public class LabelMapper {

    public List<String[]> mapLabels(List<String[]> data) {
        Map<String, Integer> labelMapping = new HashMap<>();
        labelMapping.put("positive", 2);
        labelMapping.put("neutral", 1);
        labelMapping.put("negative", 0);
        labelMapping.put("negetive", 0); // Handle typo in data (negetive -> negative).

        List<String[]> modifiedData = new ArrayList<>();

        for (String[] entry : data) {
            String[] modifiedEntry = Arrays.copyOf(entry, entry.length);
            String label = modifiedEntry[1];
            if (labelMapping.containsKey(label)) {
                modifiedEntry[1] = labelMapping.get(label).toString();
            }
            modifiedData.add(modifiedEntry);
        }

        return modifiedData;
    }
}
