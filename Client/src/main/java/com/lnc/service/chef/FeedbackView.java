package com.lnc.service.chef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.util.InputHandler;
import com.lnc.util.JsonDataFormat;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;
import java.util.logging.Logger;

public class FeedbackView {
  private final Logger logger = Logger.getLogger(FeedbackView.class.getName());

  public void getFeedbacks() {
    try {
      String menuItem = getMenuItem();

      ToJsonConversion toJson = new ToJsonConversion();
      String request = toJson.codeItemName(menuItem);

      String response = ServerConnection.requestServer(request);
      JsonDataFormat jsonDataFormat = new JsonDataFormat();
      jsonDataFormat.viewFormattedFeedbacks(response);
    } catch (JsonProcessingException ex) {
      logger.severe("Error in json parsing feedbacks: " + ex.getMessage());
    } catch (Exception ex) {
      logger.severe("Error in fetching feedbacks: " + ex.getMessage());
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
