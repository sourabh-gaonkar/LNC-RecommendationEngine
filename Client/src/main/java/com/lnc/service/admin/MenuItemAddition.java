package com.lnc.service.admin;

import com.lnc.connection.ServerConnection;
import com.lnc.model.MenuItem;
import com.lnc.model.MenuItemProfile;
import com.lnc.service.MenuItemProfileFetcher;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

import java.util.logging.Logger;

public class MenuItemAddition {
  private final Logger logger = Logger.getLogger(MenuItemAddition.class.getName());

  public void addMenuItem() {
    try{
      MenuItem item = new MenuItem();
      String itemName = InputHandler.getString("\nEnter menu item name: ");
      Double price = InputHandler.getDouble("Enter price: ");
      int choice;
      do {
        choice = InputHandler.getInt("Enter availability (1 - available, 0 - not available): ");
      } while (choice != 0 && choice != 1);
      boolean available = choice == 1;
      int categoryCode;
      do {
        categoryCode =
                InputHandler.getInt(
                        "Enter category code (1 - Breakfast, 2 - Lunch, 3 - Snack, 4 - Dinner): ");
      } while (categoryCode < 1 || categoryCode > 4);

      item.setItemName(itemName);
      item.setPrice(price);
      item.setAvailability(available);
      item.setCategory(categoryCode);

      ToJsonConversion jsonCoder = new ToJsonConversion();
      String request = jsonCoder.codeMenuItem(item, "/admin/addItem");

      String response = ServerConnection.requestServer(request);
      System.out.println("Response: " + response);

      if(response.equals("Added item to menu.")) {
        MenuItemProfileFetcher menuItemProfileFetcher = new MenuItemProfileFetcher();
        MenuItemProfile menuItemProfile = menuItemProfileFetcher.getMenuItemProfile(itemName);

        String menuItemProfileRequest = jsonCoder.codeMenuItemProfile(menuItemProfile, "/admin/addItemProfile");
        String menuItemProfileResponse = ServerConnection.requestServer(menuItemProfileRequest);
        System.out.println("Response: " + menuItemProfileResponse);
      }
    } catch (Exception ex) {
      logger.severe("Error in adding menu item: " + ex.getMessage());
    }
  }
}
