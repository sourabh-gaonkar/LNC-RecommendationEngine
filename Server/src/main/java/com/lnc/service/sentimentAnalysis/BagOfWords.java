package com.lnc.service.sentimentAnalysis;

import java.io.*;
import java.util.*;

public class BagOfWords {
    private Map<String, Integer> positiveWords;
    private Map<String, Integer> neutralWords;
    private Map<String, Integer> negativeWords;
    private Set<String> negationWords;
    private static final int SMOOTHING_FACTOR = 1;
    private static final String POSITIVE_CSV = "src/main/resources/positive_words.csv";
    private static final String NEUTRAL_CSV = "src/main/resources/neutral_words.csv";
    private static final String NEGATIVE_CSV = "src/main/resources/negative_words.csv";

    public BagOfWords() {
        positiveWords = new HashMap<>();
        neutralWords = new HashMap<>();
        negativeWords = new HashMap<>();
        negationWords = new HashSet<>(Arrays.asList("not", "never", "no"));
        loadCSVFiles();
    }

    private void loadCSVFiles() {
        loadCSV(POSITIVE_CSV, positiveWords);
        loadCSV(NEUTRAL_CSV, neutralWords);
        loadCSV(NEGATIVE_CSV, negativeWords);
    }

    private void loadCSV(String filePath, Map<String, Integer> wordMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip the header row
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    wordMap.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCSV(String filePath, Map<String, Integer> wordMap) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("word,count"); // Write header
            bw.newLine();
            for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processWords(List<String[]> data) {
        for (String[] entry : data) {
            String cleanedString = entry[0];
            int label = Integer.parseInt(entry[1]);

            String[] words = cleanedString.split("\\s+");
            boolean negate = false;
            for (String word : words) {
                if (negationWords.contains(word.toLowerCase())) {
                    negate = true;
                } else {
                    String processedWord = negate ? "NOT_" + word : word;
                    negate = false; // Reset negate after using it
                    if (label == 2) {
                        positiveWords.put(processedWord, positiveWords.getOrDefault(processedWord, 0) + 1);
                    } else if (label == 1) {
                        neutralWords.put(processedWord, neutralWords.getOrDefault(processedWord, 0) + 1);
                    } else if (label == 0) {
                        negativeWords.put(processedWord, negativeWords.getOrDefault(processedWord, 0) + 1);
                    }
                }
            }
        }
        updateCSV(POSITIVE_CSV, positiveWords);
        updateCSV(NEUTRAL_CSV, neutralWords);
        updateCSV(NEGATIVE_CSV, negativeWords);
    }

    public void updateWords(String cleanedString, int label) {
        String[] words = cleanedString.split("\\s+");
        boolean negate = false;
        for (String word : words) {
            if (negationWords.contains(word.toLowerCase())) {
                negate = true;
            } else {
                String processedWord = negate ? "NOT_" + word : word;
                negate = false; // Reset negate after using it
                if (label == 2) {
                    positiveWords.put(processedWord, positiveWords.getOrDefault(processedWord, 0) + 1);
                } else if (label == 1) {
                    neutralWords.put(processedWord, neutralWords.getOrDefault(processedWord, 0) + 1);
                } else if (label == 0) {
                    negativeWords.put(processedWord, negativeWords.getOrDefault(processedWord, 0) + 1);
                }
            }
        }
        updateCSV(POSITIVE_CSV, positiveWords);
        updateCSV(NEUTRAL_CSV, neutralWords);
        updateCSV(NEGATIVE_CSV, negativeWords);
    }

    public Map<String, Integer> getPositiveWords() {
        return positiveWords;
    }

    public Map<String, Integer> getNeutralWords() {
        return neutralWords;
    }

    public Map<String, Integer> getNegativeWords() {
        return negativeWords;
    }

    public int getSmoothingFactor() {
        return SMOOTHING_FACTOR;
    }
}
