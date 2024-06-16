package com.lnc.model;

public class Review {
    private String text;
    private String label;

    public Review(String text, String label) {
        this.text = text;
        this.label = label;
    }

    public String getText() {
        return text;
    }

    public String getLabel() {
        return label;
    }
}
