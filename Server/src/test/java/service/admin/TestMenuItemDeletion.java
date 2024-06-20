package service.admin;

import com.lnc.service.admin.MenuItemDeletion;
import org.junit.jupiter.api.Test;

public class TestMenuItemDeletion {
    @Test
    public void testUnavailableMenuItemDeletion() {
        String jsonData = "{\"itemName\":\"Imaginary Item\"}";

        MenuItemDeletion menuItemDeletion = new MenuItemDeletion();
        String response = menuItemDeletion.deleteMenuItem(jsonData);

        assert(response.equals("Menu item not found."));
    }

    @Test
    public void testInvalidJsonData() {
        String jsonData = "{\"item_Name\":\"Imaginary Item\"}";

        MenuItemDeletion menuItemDeletion = new MenuItemDeletion();
        String response = menuItemDeletion.deleteMenuItem(jsonData);

        assert(response.equals("Error deleting menu item."));
    }
}
