package com.lnc.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.MenuQueries;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuItemDeletion {

  private static final Logger logger = Logger.getLogger(MenuItemDeletion.class.getName());

  private final ConversionFromJson jsonDecoder = new ConversionFromJson();
  private final MenuQueries menuQueries = new MenuQueries();

  public String deleteMenuItem(String jsonData) {
    try {
      String itemName = jsonDecoder.getJsonValue("itemName", jsonData);

      if (!menuQueries.checkMenuItemPresent(itemName)) {
        return "Menu item not found.";
      }

      boolean isDeleted = menuQueries.deleteMenuItem(itemName);
      return isDeleted ? "Deleted Item Successfully" : "Error deleting menu item.";

    } catch (JsonProcessingException e) {
      logger.log(Level.SEVERE, "JSON processing error: " + e.getMessage(), e);
    } catch (NullPointerException e) {
      logger.log(Level.SEVERE, "Null pointer exception: " + e.getMessage(), e);
    }
    return "Error deleting menu item.";
  }
}
