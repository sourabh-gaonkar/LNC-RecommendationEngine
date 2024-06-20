package com.lnc.service.admin;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.MenuQueries;
import com.lnc.model.MenuItem;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Logger;

public class MenuItemAddition {
  private Logger logger = Logger.getLogger(MenuItemAddition.class.getName());

  public String addMenuItem(String jsonData) {
    try {
      MenuItem menuItem = decodeJsonToMenuItem(jsonData);
      MenuQueries menuQueries = new MenuQueries();

      if (isItemPresentInMenu(menuQueries, menuItem.getItemName())) {
        return "Item already present in menu.";
      }

      if (addItemToMenu(menuQueries, menuItem)) {
        return "Added item to menu.";
      } else {
        return "Error adding menu item.";
      }
    } catch (Exception e) {
      logger.severe("Error processing menu item addition: " + e.getMessage());
      return "Error processing menu item addition.";
    }
  }

  private MenuItem decodeJsonToMenuItem(String jsonData) throws JsonProcessingException, NullPointerException  {
    ConversionFromJson jsonDecoder = new ConversionFromJson();
    return jsonDecoder.decodeMenuItem(jsonData);
  }

  private boolean isItemPresentInMenu(MenuQueries menuQueries, String itemName) {
    return menuQueries.checkMenuItemPresent(itemName);
  }

  private boolean addItemToMenu(MenuQueries menuQueries, MenuItem menuItem) {
    return menuQueries.addMenuItem(menuItem);
  }
}