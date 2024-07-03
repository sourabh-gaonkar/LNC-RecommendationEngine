package com.lnc.service.chef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackDisplayTest {

    private FeedbackDisplay feedbackDisplayUnderTest;

    @BeforeEach
    void setUp() {
        feedbackDisplayUnderTest = new FeedbackDisplay();
    }

    @Test
    void testDisplayFeedback() {
        String jsonData = "{\"key\":\"invalid_template\"}";

        String result = feedbackDisplayUnderTest.displayFeedback(jsonData);

        assertEquals("Invalid item name.", result);
    }
}
