package com.lnc.service.admin;

import com.lnc.connection.ServerConnection;
import com.lnc.model.MenuItem;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

public class MenuItemUpdate {
    public void updateMenuItem() throws Exception {
        MenuItem item = new MenuItem();
        String itemName = InputHandler.getString("\nEnter menu item name: ");
        Double price = InputHandler.getDouble("Enter price: ");
        int choice;
        do{
            choice = InputHandler.getInt("Enter availability (1 - available, 0 - not available): ");
        } while (choice != 0 && choice != 1);
        boolean available = choice == 1 ? true : false;
        int categoryCode;
        do {
            categoryCode = InputHandler.getInt("Enter category code (1 - Breakfast, 2 - Lunch, 3 - Snack, 4 - Dinner): ");
        } while (categoryCode < 1 || categoryCode > 4);

        item.setItemName(itemName);
        item.setPrice(price);
        item.setAvailability(available);
        item.setCategory(categoryCode);

        ToJsonConversion jsonCoder = new ToJsonConversion();
        String request = jsonCoder.codeMenuItem(item, "/admin/updateItem");

        String response = ServerConnection.requestServer(request);
        System.out.println("Response: " + response);
    }
}
