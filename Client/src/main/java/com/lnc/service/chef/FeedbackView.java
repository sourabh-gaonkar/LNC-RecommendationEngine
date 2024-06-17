package com.lnc.service.chef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.util.InputHandler;
import com.lnc.util.JsonDataFormat;
import com.lnc.util.ToJsonConversion;
import java.io.IOException;

public class FeedbackView {
  public void getFeedbacks() throws IOException {
    String menuItem = getMenuItem();

    ToJsonConversion toJson = new ToJsonConversion();
    String request = toJson.codeItemName(menuItem);

    try {
      String response = ServerConnection.requestServer(request);
      JsonDataFormat jsonDataFormat = new JsonDataFormat();
      jsonDataFormat.viewFormattedFeedbacks(response);
    } catch (JsonProcessingException ex) {
      System.out.println("Enter valid menu Item");
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private String getMenuItem() throws IOException {
    String menuItem;
    while (true) {
      menuItem = InputHandler.getString("\nEnter menu item: ");
      if (menuItem.isEmpty()) {
        System.out.println("Comment cannot be empty.");
        continue;
      }
      break;
    }
    return menuItem;
  }
}
