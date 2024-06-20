package com.lnc.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.util.JsonDataFormat;

import java.util.logging.Logger;

public class MenuView {
  private final Logger logger = Logger.getLogger(MenuView.class.getName());
  public void displayMenu() {
    String apiPath = "/admin/viewItems";
    String request = apiPath + "& ";
    String response = ServerConnection.requestServer(request);
    if(response == null) {
      System.out.println("No menu items found.");
      return;
    }

    JsonDataFormat jsonFormatter = new JsonDataFormat();
    System.out.println();
    try{
      jsonFormatter.prettyView(response);
    } catch (Exception ex) {
      logger.severe("Error in displaying menu items: " + ex.getMessage());
    }
  }
}
