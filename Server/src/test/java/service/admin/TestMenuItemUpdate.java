package service.admin;

import com.lnc.service.admin.MenuItemUpdate;
import org.junit.jupiter.api.Test;

public class TestMenuItemUpdate {
    @Test
    public void testUnavailableItemUpdates() {
        String jsonString = "{\"price\":50.0,\"availability\":true,\"category\":\"BREAKFAST\",\"item_name\":\"Imaginary Item\"}";

        MenuItemUpdate menuItemUpdate = new MenuItemUpdate();
        String response = menuItemUpdate.updateMenuItem(jsonString);

        assert(response.equals("Item not found."));
    }

    @Test
    public void testInvalidJsonData() {
    String jsonString =
        "{\"price\":50.0,\"availability\":true,\"category\":\"BREAKFAST\",\"itemName\":\"Imaginary Item\"}";

        MenuItemUpdate menuItemUpdate = new MenuItemUpdate();
        String response = menuItemUpdate.updateMenuItem(jsonString);

        assert(response.equals("Error updating menu item."));
    }
}
