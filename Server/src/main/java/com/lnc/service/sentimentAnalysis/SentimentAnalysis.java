package com.lnc.service.sentimentAnalysis;

import com.lnc.DB.FeedbackQueries;
import com.lnc.DB.ReviewQueries;
import com.lnc.model.Review;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import libsvm.svm_model;

public class SentimentAnalysis {
    private final FeedbackQueries feedbackQueries = new FeedbackQueries();

    public SentimentAnalysis() throws SQLException {
    }

    public List<Map<String, Object>> getSentiAnalysis(List<Map<String, Object>> menu) throws Exception {
        List<Map<String, Object>> updatedMenu = new ArrayList<>();

        ReviewQueries reviewQueries = new ReviewQueries();
        List<Review> reviews = reviewQueries.getReviews();

        SVMModel svmModel = new SVMModel();
        int folds = 100;
        double accuracy = svmModel.performStratifiedCrossValidation(reviews, folds);
        System.out.println("-----------------------------------------------------------Accuracy: " + accuracy);

        for (Map<String, Object> item : menu) {
            String itemName = (String) item.get("item_name");
            Double price = (Double) item.get("price");
            String category = (String) item.get("category");
            List<String> comments = feedbackQueries.getReviewCommentsOfItem(itemName);

            if (comments.isEmpty()) {
                System.out.println("No reviews found for " + itemName);
                return null;
            }

            svm_model model = svmModel.trainSVM(reviews);
            String itemSentiment = null;
            double sentimentScore = 0;
            for (String review : comments) {
                sentimentScore += svmModel.predictSentiment(model, review);
                if(sentimentScore/comments.size() > 0.05) {
                    itemSentiment = "Positive";
                } else if(sentimentScore/comments.size() < -0.05) {
                    itemSentiment = "Negative";
                } else {
                    itemSentiment = "Neutral";
                }
            }
            Map<String, Object> menuItem = Map.of("item_name", itemName, "price", price, "sentiment", itemSentiment, "category", category);
            updatedMenu.add(menuItem);
        }
        return updatedMenu;
    }
}
