package com.lnc.service.admin;

import com.lnc.DB.Menu;
import com.lnc.model.MenuItem;
import com.lnc.utils.FromJson;

public class MenuItemAddition {
  public String addMenuItem(String jsonData) throws Exception {
    FromJson jsonDecoder = new FromJson();
    MenuItem item = jsonDecoder.decodeMenuItem(jsonData);

    Menu menu = new Menu();
    if (!menu.checkMenuItemPresent(item.getItemName())) {
      if (menu.addMenuItem(item)) {
        return "Added item to menu.";
      }
    } else {
      return "Item already present in menu.";
    }
    return "Error adding menu item.";
  }
}
