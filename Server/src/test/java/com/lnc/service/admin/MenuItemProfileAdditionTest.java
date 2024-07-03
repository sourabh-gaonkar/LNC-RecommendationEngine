package com.lnc.service.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuItemProfileAdditionTest {

    private MenuItemProfileAddition menuItemProfileAdditionUnderTest;

    @BeforeEach
    void setUp() {
        menuItemProfileAdditionUnderTest = new MenuItemProfileAddition();
    }

    @Test
    void testAddMenuItemProfile() {
        String jsonData  = "{\"key\":\"invalid template\"}";

        String result = menuItemProfileAdditionUnderTest.addMenuItemProfile(jsonData);

        assertEquals("Error in adding menu item profile.", result);
    }
}
