package com.lnc.service.sentimentAnalysis;

import java.util.*;
import java.util.stream.Collectors;

public class TextCleaner {

    public String cleanText(String text) {
        text = text.replaceAll("[^a-zA-Z0-9\\s]", "");

        text = text.toLowerCase();

        List<String> words = Arrays.asList(text.split("\\s+"));

        List<String> filteredWords = removeStopwords(words);

        return String.join(" ", filteredWords);
    }

    private static List<String> removeStopwords(List<String> words) {
        Set<String> stopwords = getStopwords();
        return words.stream()
                .filter(word -> !stopwords.contains(word))
                .collect(Collectors.toList());
    }

    private static Set<String> getStopwords() {
        String[] stopwordsArray = {
                "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
                "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself",
                "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which",
                "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was", "were", "be",
                "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an",
                "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for",
                "with", "about", "against", "between", "into", "through", "during", "before", "after", "above",
                "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further",
                "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few",
                "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
                "too", "very", "s", "t", "can", "will", "just", "don", "should", "now", "let", "lets", "isn't", "aren't",
                "wasn't", "weren't", "hasn't", "haven't", "hadn't", "doesn't", "don't", "didn't", "won't", "wouldn't",
                "shan't", "shouldn't", "mustn't", "can't", "cannot", "couldn't", "mightn't", "needn't", "i'm", "you're",
                "he's", "she's", "it's", "we're", "they're", "i've", "you've", "we've", "they've", "i'd", "you'd", "he'd",
                "she'd", "we'd", "they'd", "i'll", "you'll", "he'll", "she'll", "we'll", "they'll", "isn’t", "aren’t",
                "wasn’t", "weren’t", "hasn’t", "haven’t", "hadn’t", "doesn’t", "don’t", "didn’t", "won’t", "wouldn’t",
                "shan’t", "shouldn’t", "mustn’t", "can’t", "cannot", "couldn’t", "mightn’t", "needn’t", "i’m", "you’re",
                "he’s", "she’s", "it’s", "we’re", "they’re", "i’ve", "you’ve", "we’ve", "they’ve", "i’d", "you’d", "he’d",
                "she’d", "we’d", "they’d", "i’ll", "you’ll", "he’ll", "she’ll", "we’ll", "they’ll", "ours", "theirs",
                "her", "their", "whose", "whereupon", "whoever", "whereas", "wherein", "whither", "whatever", "whenever"
        };

        return new HashSet<>(Arrays.asList(stopwordsArray));
    }
}

