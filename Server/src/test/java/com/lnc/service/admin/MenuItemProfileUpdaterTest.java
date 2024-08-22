package com.lnc.service.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuItemProfileUpdaterTest {

    private MenuItemProfileUpdater menuItemProfileUpdaterUnderTest;

    @BeforeEach
    void setUp() {
        menuItemProfileUpdaterUnderTest = new MenuItemProfileUpdater();
    }

    @Test
    void testUpdateMenuItemProfile() {
        String jsonData  = "{\"key\":\"invalid template\"}";

        String result = menuItemProfileUpdaterUnderTest.updateMenuItemProfile(jsonData);

        assertEquals("Error in updating menu item profile.", result);
    }
}
