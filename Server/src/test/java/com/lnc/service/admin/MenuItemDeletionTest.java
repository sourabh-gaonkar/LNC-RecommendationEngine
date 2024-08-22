package com.lnc.service.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuItemDeletionTest {

    private MenuItemDeletion menuItemDeletionUnderTest;

    @BeforeEach
    void setUp() {
        menuItemDeletionUnderTest = new MenuItemDeletion();
    }

    @Test
    void testDeleteMenuItemForInvalidItem() {
        String jsonData = "{\"itemName\":\"Invalid Item Name\"}";

        String result = menuItemDeletionUnderTest.deleteMenuItem(jsonData);

        assertEquals("Menu item not found.", result);
    }

    @Test
    void testDeleteMenuItemForInvalidJsonTemplate() {
        String jsonData = "{\"item\":\"Invalid Template\"}";

        String result = menuItemDeletionUnderTest.deleteMenuItem(jsonData);

        assertEquals("Error deleting menu item.", result);
    }
}
