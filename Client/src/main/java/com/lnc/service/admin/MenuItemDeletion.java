package com.lnc.service.admin;

import com.lnc.connection.ServerConnection;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

public class MenuItemDeletion {
  public void deleteMenuItem() throws Exception {
    String itemName = InputHandler.getString("\nEnter item name: ");

    ToJsonConversion jsonCoder = new ToJsonConversion();
    String request = jsonCoder.codeMenuItemName(itemName);

    String response = ServerConnection.requestServer(request);
    System.out.println("Response: " + response);
  }
}
