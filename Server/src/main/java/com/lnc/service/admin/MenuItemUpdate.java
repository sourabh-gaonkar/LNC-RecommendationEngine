package com.lnc.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.MenuQueries;
import com.lnc.model.MenuItem;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Logger;

public class MenuItemUpdate {
  private static final Logger logger = Logger.getLogger(MenuItemUpdate.class.getName());

  public String updateMenuItem(String jsonData) {
    try {
      MenuItem item = decodeJsonToMenuItem(jsonData);

      MenuQueries menuQueries = new MenuQueries();
      if (menuQueries.checkMenuItemPresent(item.getItemName())) {
        if (menuQueries.updateMenuItem(item)) {
          return "Updated menu item.";
        } else {
          return "Failed to update menu item.";
        }
      } else {
        return "Item not found.";
      }
    } catch (JsonProcessingException | NullPointerException e) {
      logger.severe("Error updating menu item: " + e.getMessage());
      return "Error updating menu item.";
    }
  }

  private MenuItem decodeJsonToMenuItem(String jsonData) throws JsonProcessingException {
    ConversionFromJson jsonDecoder = new ConversionFromJson();
    return jsonDecoder.decodeMenuItem(jsonData);
  }
}