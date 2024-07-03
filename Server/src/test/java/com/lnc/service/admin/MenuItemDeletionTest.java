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

        menuItemDeletionUnderTest.deleteMenuItem(jsonData);

        assertEquals("Menu item not found.", menuItemDeletionUnderTest.deleteMenuItem(jsonData));
    }

    @Test
    void testDeleteMenuItemForInvalidJsonTemplate() {
        String jsonData = "{\"item\":\"Invalid Template\"}";

        menuItemDeletionUnderTest.deleteMenuItem(jsonData);

        assertEquals("Error deleting menu item.", menuItemDeletionUnderTest.deleteMenuItem(jsonData));
    }
}
