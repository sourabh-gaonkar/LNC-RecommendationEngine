package com.lnc.service.admin;

import com.lnc.DB.MenuQueries;
import com.lnc.utils.FromJson;

public class MenuItemDeletion {
  public String deleteMenuItem(String jsonData) throws Exception {
    FromJson jsonDecoder = new FromJson();
    String itemName = jsonDecoder.getJsonValue("itemName", jsonData);

    MenuQueries menu = new MenuQueries();
    if (menu.checkMenuItemPresent(itemName)) {
      if (menu.deleteMenuItem(itemName)) {
        return "Deleted Item Successfully";
      }
    } else {
      return "Menu item not found..";
    }
    return "Error deleting menu item..";
  }
}
