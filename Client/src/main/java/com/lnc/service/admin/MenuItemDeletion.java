package com.lnc.service.admin;

import com.lnc.connection.ServerConnection;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

import java.util.logging.Logger;

public class MenuItemDeletion {
  private final Logger logger = Logger.getLogger(MenuItemDeletion.class.getName());

  public void deleteMenuItem() {
    try {
      String itemName = InputHandler.getString("\nEnter item name: ");

      ToJsonConversion jsonCoder = new ToJsonConversion();
      String request = jsonCoder.codeMenuItemName(itemName);

      String response = ServerConnection.requestServer(request);
      System.out.println("Response: " + response);
    } catch (Exception ex) {
      logger.severe("Error deleting menu item: " + ex.getMessage());
    }
  }
}
