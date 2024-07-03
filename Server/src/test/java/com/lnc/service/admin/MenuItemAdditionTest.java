package com.lnc.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.utils.ConversionFromJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class MenuItemAdditionTest {

    private MenuItemAddition menuItemAdditionUnderTest;

    @BeforeEach
    void setUp() {
        menuItemAdditionUnderTest = new MenuItemAddition();
    }

    @Test
    void testAddMenuItem() throws JsonProcessingException {
        String jsonData = "{\"key\":\"invalid_template\"}";

        String result = menuItemAdditionUnderTest.addMenuItem(jsonData);

        assertEquals("Error processing menu item addition.", result);
    }
}
