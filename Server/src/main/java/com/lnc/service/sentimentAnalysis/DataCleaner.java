package com.lnc.service.sentimentAnalysis;

import java.util.Iterator;
import java.util.List;

public class DataCleaner {
    public List<String[]> cleanData(List<String[]> data, int labelIndex, int cleanedTextIndex) {
        Iterator<String[]> iterator = data.iterator();
        while (iterator.hasNext()) {
            String[] row = iterator.next();
            if (row[labelIndex] == null || row[cleanedTextIndex] == null) {
                iterator.remove();
            }
        }

        return data;
    }
}
