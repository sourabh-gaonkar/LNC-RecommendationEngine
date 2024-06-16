package com.lnc.service.admin;

import com.lnc.DB.Menu;
import com.lnc.utils.FromJson;

public class MenuItemDeletion {
  public String deleteMenuItem(String jsonData) throws Exception {
    FromJson jsonDecoder = new FromJson();
    String itemName = jsonDecoder.getJsonValue("itemName", jsonData);

    Menu menu = new Menu();
    if (menu.checkMenuItemPresent(itemName)) {
      if (menu.deleteMenuItem(itemName)) {
        return "Deleted Item Successfully";
      }
    }
    return "Error deleting menu item..";
  }
}
