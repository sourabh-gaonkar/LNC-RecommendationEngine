package com.lnc.service.employee.improviseItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerSubmissionTest {

    private AnswerSubmission answerSubmissionUnderTest;

    @BeforeEach
    void setUp() {
        answerSubmissionUnderTest = new AnswerSubmission();
    }

    @Test
    void testSubmitAnswer() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String result = answerSubmissionUnderTest.submitAnswer(jsonData);

        assertEquals("Server error while submitting answer.", result);
    }
}
