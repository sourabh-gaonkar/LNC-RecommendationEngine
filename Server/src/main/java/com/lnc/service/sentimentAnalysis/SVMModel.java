package com.lnc.service.sentimentAnalysis;

import com.lnc.model.Review;
import libsvm.*;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.SimpleTokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SVMModel {
    private static final SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
    private static final PorterStemmer stemmer = new PorterStemmer();
    private final Tokenize tokenize = new Tokenize();
    public double performStratifiedCrossValidation(List<Review> reviews, int folds) {
        Map<String, List<Review>> reviewsByLabel = reviews.stream()
                .collect(Collectors.groupingBy(Review::getLabel));

        double totalAccuracy = 0.0;

        for (int i = 0; i < folds; i++) {
            List<Review> testSet = new ArrayList<>();
            List<Review> trainingSet = new ArrayList<>();

            for (Map.Entry<String, List<Review>> entry : reviewsByLabel.entrySet()) {
                List<Review> labelReviews = entry.getValue();
                int testSetSize = labelReviews.size() / folds;
                testSet.addAll(labelReviews.subList(i * testSetSize, Math.min((i + 1) * testSetSize, labelReviews.size())));
                trainingSet.addAll(labelReviews.subList(0, i * testSetSize));
                trainingSet.addAll(labelReviews.subList(Math.min((i + 1) * testSetSize, labelReviews.size()), labelReviews.size()));
            }

            svm_model model = trainSVM(trainingSet);
            double accuracy = evaluateModel(model, testSet);
            totalAccuracy += accuracy;
        }

        return totalAccuracy / folds;
    }

    public svm_model trainSVM(List<Review> reviews) {
        svm_problem problem = new svm_problem();
        problem.l = reviews.size();
        problem.x = new svm_node[reviews.size()][];
        problem.y = new double[reviews.size()];

        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            String text = preprocessReview(review.getText().trim());
            if (!text.isEmpty()) {
                problem.x[i] = tokenize.tokenizeReview(text);
                problem.y[i] = convertLabelToDouble(review.getLabel());
            } else {
                problem.x[i] = new svm_node[0];
                problem.y[i] = 0.0;
            }
        }

        svm_parameter params = new svm_parameter();
        params.kernel_type = svm_parameter.RBF;
        params.C = 10;
        params.gamma = 0.1;
        params.eps = 0.001;

        return svm.svm_train(problem, params);
    }

    public String preprocessReview(String text) {
        // Convert to lower case
        text = text.toLowerCase();
        // Remove punctuation
        text = text.replaceAll("[^a-zA-Z0-9\\s]", "");
        // Tokenize
        String[] tokens = tokenizer.tokenize(text);
        // Stem tokens
        tokens = Arrays.stream(tokens)
                .map(stemmer::stem)
                .toArray(String[]::new);
        // Remove stopwords (simple example, consider using a proper stopword list)
        List<String> stopwords = Arrays.asList("the", "is", "at", "of", "on", "and", "a");
        tokens = Arrays.stream(tokens)
                .filter(token -> !stopwords.contains(token))
                .toArray(String[]::new);
        return String.join(" ", tokens);
    }

    private double evaluateModel(svm_model model, List<Review> testSet) {
        int correct = 0;
        for (Review review : testSet) {
            double predictedLabel = predictSentiment(model, review.getText());
            if (convertDoubleToLabel(predictedLabel).equals(review.getLabel())) {
                correct++;
            }
        }
        return (double) correct / testSet.size();
    }

    private static double convertLabelToDouble(String label) {
        switch (label.toLowerCase()) {
            case "positive":
                return 1.0;
            case "negative":
                return -1.0;
            default:
                return 0.0;
        }
    }

    private String convertDoubleToLabel(double value) {
        if (value == 1.0) {
            return "positive";
        } else if (value == -1.0) {
            return "negative";
        } else {
            return "neutral";
        }
    }

    public double predictSentiment(svm_model model, String text) {
        String preprocessedText = preprocessReview(text);
        svm_node[] nodes = tokenize.tokenizeReview(preprocessedText);
        return svm.svm_predict(model, nodes);
    }
}

