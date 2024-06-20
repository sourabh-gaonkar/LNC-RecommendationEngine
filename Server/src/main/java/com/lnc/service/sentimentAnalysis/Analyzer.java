package com.lnc.service.sentimentAnalysis;

public class Analyzer {
    private final BagOfWords bagOfWords;

    public Analyzer(BagOfWords bow) {
        this.bagOfWords = bow;
    }

    public String predictSentiment(String cleanedString) {
        String[] words = cleanedString.split("\\s+");
        int positiveScore = 0;
        int neutralScore = 0;
        int negativeScore = 0;
        boolean negateNext = false;

        for (String word : words) {
            String cleanedWord = word.toLowerCase();
            if (cleanedWord.equals("not") || cleanedWord.equals("never")) {
                negateNext = true;
            } else {
                if (negateNext) {
                    word = "NOT_" + word;
                    negateNext = false;
                }

                positiveScore += bagOfWords.getPositiveWords().getOrDefault(word, bagOfWords.getSmoothingFactor());
                neutralScore += bagOfWords.getNeutralWords().getOrDefault(word, bagOfWords.getSmoothingFactor());
                negativeScore += bagOfWords.getNegativeWords().getOrDefault(word, bagOfWords.getSmoothingFactor());
            }
        }

        if (positiveScore > neutralScore && positiveScore > negativeScore) {
            return "Positive";
        } else if (neutralScore > positiveScore && neutralScore > negativeScore) {
            return "Neutral";
        } else if (negativeScore > positiveScore && negativeScore > neutralScore) {
            return "Negative";
        } else {
            return "Neutral";  // Default to neutral if there's a tie
        }
    }
}

