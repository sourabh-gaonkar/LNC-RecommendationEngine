package com.lnc.service.sentimentAnalysis;

import com.lnc.DB.FeedbackQueries;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SentimentAnalysis {
    private final FeedbackQueries feedbackQueries = new FeedbackQueries();
    private final TextCleaner textCleaner = new TextCleaner();
    private final LabelMapper labelMapper = new LabelMapper();
    private final NullDataCleaner nullDataCleaner = new NullDataCleaner();

    public SentimentAnalysis() {
    }

    public List<Map<String, Object>> getSentimentAnalysis(List<Map<String, Object>> menu) {
        List<Map<String, Object>> updatedMenu = new ArrayList<>();

        for (Map<String, Object> item : menu) {
            String itemName = (String) item.get("item_name");
            Double price = (Double) item.get("price");
            String category = (String) item.get("category");
            Double averageRating = (Double) item.get("average_rating");

            List<String[]> reviews = feedbackQueries.getReviewCommentsOfItem(itemName);

            if (reviews.isEmpty()) {
                System.out.println("No reviews found for " + itemName);
                return null;
            }

            List<String[]> cleanedReviewList = new ArrayList<>();
            for (String[] record : reviews) {
                String review = record[0];
                String sentiment = record[1];
                String cleanedReview = textCleaner.cleanText(review);
                cleanedReviewList.add(new String[]{cleanedReview, sentiment});
            }

            List<String[]> modifiedData = labelMapper.mapLabels(cleanedReviewList);
            List<String[]> validData = nullDataCleaner.cleanData(modifiedData, 1, 0);

            BagOfWords bow = new BagOfWords();
            bow.processWords(validData);

            String itemSentiment = getItemSentiment(bow, reviews);

            Map<String, Object> menuItem = Map.of("item_name", itemName, "price", price, "sentiment", itemSentiment, "category", category, "average_rating", averageRating);
            updatedMenu.add(menuItem);
        }
        return updatedMenu;
    }

    private String getItemSentiment(BagOfWords bow, List<String[]> reviews) {
        String overallSentiment = null;
        Analyzer analyzer = new Analyzer(bow);
        int positiveScore = 0;
        int negativeScore = 0;
        int neutralScore = 0;
        double totalScore = 0;

        for (String[] review : reviews) {
            String sentiment = analyzer.predictSentiment(review[0]);
            if (sentiment.equals("Positive")) {
                positiveScore += 1;
            } else if (sentiment.equals("Negative")) {
                negativeScore += 1;
            } else {
                neutralScore += 1;
            }
            if(review[1].equals("positive")) {
                totalScore += 2;
            } else if(review[1].equals("neutral")) {
                totalScore += 1;
            }
        }
        System.out.println("Positive: " + positiveScore + ", Negative: " + negativeScore + ", Neutral: " + neutralScore + ", Total: " + totalScore/reviews.size());

        if (positiveScore > negativeScore && positiveScore > neutralScore) {
            overallSentiment = "Positive";
        } else if (negativeScore > positiveScore && negativeScore > neutralScore) {
            overallSentiment = "Negative";
        } else {
            overallSentiment = "Neutral";
        }

        return overallSentiment;
    }
}
