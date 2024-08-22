package com.lnc.service.recommendationEngine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecommendationEngineTest {

    private RecommendationEngine recommendationEngineUnderTest;

    @BeforeEach
    void setUp() {
        recommendationEngineUnderTest = new RecommendationEngine();
    }

    @Test
    void testRunEngine() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String result = recommendationEngineUnderTest.runEngine(jsonData);

        assertEquals("Error in Recommendation Engine.", result);
    }
}
