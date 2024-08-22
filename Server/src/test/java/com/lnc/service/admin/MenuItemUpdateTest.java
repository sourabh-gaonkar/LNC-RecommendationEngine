package com.lnc.service.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuItemUpdateTest {

    private MenuItemUpdate menuItemUpdateUnderTest;

    @BeforeEach
    void setUp() {
        menuItemUpdateUnderTest = new MenuItemUpdate();
    }

    @Test
    void testDeleteMenuItemForInvalidItem() {
        String jsonData = "{\"price\":1.50,\"item_name\":\"Invalid Item Name\",\"availability\":true,\"category\":\"BREAKFAST\"}";

        String result = menuItemUpdateUnderTest.updateMenuItem(jsonData);

        assertEquals("Item not found.", result);
    }

    @Test
    void testDeleteMenuItemForInvalidJsonTemplate() {
        String jsonData = "{\"item\":\"Invalid Template\"}";

        String result = menuItemUpdateUnderTest.updateMenuItem(jsonData);

        assertEquals("Error updating menu item.", result);
    }
}
