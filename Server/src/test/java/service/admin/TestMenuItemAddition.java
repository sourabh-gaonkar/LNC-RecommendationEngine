package service.admin;

import com.lnc.service.admin.MenuItemAddition;
import org.junit.jupiter.api.Test;

public class TestMenuItemAddition {
    @Test
    public void testDuplicateItemAddition() {
        String jsonData = "{\"price\":65.0,\"availability\":true,\"category\":\"SNACK\",\"item_name\":\"Masala Dosa\"}";

        MenuItemAddition menuItemAddition = new MenuItemAddition();
        String response = menuItemAddition.addMenuItem(jsonData);

        assert(response.equals("Item already present in menu."));
    }

    @Test
    public void testInvalidJsonData() {
        String jsonData = "{\"jsonString\":\"Invalid JSON data\"}";

        MenuItemAddition menuItemAddition = new MenuItemAddition();
        String response = menuItemAddition.addMenuItem(jsonData);

        assert(response.equals("Error processing menu item addition."));
    }
}
