package com.lnc.service.chef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RolloutMenuTest {

    private RolloutMenu rolloutMenuUnderTest;

    @BeforeEach
    void setUp() {
        rolloutMenuUnderTest = new RolloutMenu();
    }

    @Test
    void testRolloutMenu() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String response = rolloutMenuUnderTest.rolloutMenu(jsonData);

        assertEquals("Menu rollout failed", response);
    }
}
