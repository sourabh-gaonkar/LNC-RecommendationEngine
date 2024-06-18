package com.lnc.service.admin;

import com.lnc.DB.MenuQueries;
import com.lnc.model.MenuItem;
import com.lnc.utils.FromJson;

public class MenuItemUpdate {
  public String updateMenuItem(String jsonData) throws Exception {
    FromJson jsonDecoder = new FromJson();
    MenuItem item = jsonDecoder.decodeMenuItem(jsonData);

    MenuQueries menu = new MenuQueries();
    if (menu.checkMenuItemPresent(item.getItemName())) {
      if (menu.updateMenuItem(item)) {
        return "Updated menu item.";
      }
    } else {
      return "Item not found.";
    }

    return "Error updating menu item.";
  }
}
