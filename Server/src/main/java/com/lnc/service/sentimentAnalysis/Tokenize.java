package com.lnc.service.sentimentAnalysis;

import libsvm.svm_node;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

import java.util.*;
import java.util.stream.Collectors;

public class Tokenize {
    private final SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
    public svm_node[] tokenizeReview(String text) {
        Span[] spans = tokenizer.tokenizePos(text);
        List<String> tokens = Arrays.stream(spans)
                .map(span -> span.getCoveredText(text).toString())
                .collect(Collectors.toList());

        Map<String, Integer> tokenCounts = new HashMap<>();
        for (String token : tokens) {
            tokenCounts.put(token, tokenCounts.getOrDefault(token, 0) + 1);
        }

        List<svm_node> nodes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tokenCounts.entrySet()) {
            svm_node node = new svm_node();
            node.index = entry.getKey().hashCode();
            node.value = entry.getValue();
            nodes.add(node);
        }

        return nodes.toArray(new svm_node[0]);
    }
}

