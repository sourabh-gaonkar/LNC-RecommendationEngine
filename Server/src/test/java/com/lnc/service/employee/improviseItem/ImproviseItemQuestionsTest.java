package com.lnc.service.employee.improviseItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImproviseItemQuestionsTest {

    private ImproviseItemQuestions improviseItemQuestionsUnderTest;

    @BeforeEach
    void setUp() {
        improviseItemQuestionsUnderTest = new ImproviseItemQuestions();
    }

    @Test
    void testGetQuestions() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String response = improviseItemQuestionsUnderTest.getQuestions(jsonData);

        assertEquals("Failed to get questions.", response);
    }
}
