package com.lnc.service.chef;

import com.lnc.connection.ServerConnection;
import com.lnc.util.InputHandler;
import com.lnc.util.JsonDataFormat;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;
import java.util.logging.Logger;

public class EngineRecommendation {
  private final Logger logger = Logger.getLogger(EngineRecommendation.class.getName());
  private final JsonDataFormat jsonFormatter = new JsonDataFormat();
  private final ToJsonConversion toJsonConverter = new ToJsonConversion();
  public void viewRecommendation() {
    try{
      int itemCount = getItemCount();
      String request = toJsonConverter.codeItemsCount(itemCount);
      String response = ServerConnection.requestServer(request);
      if(response.equals("Error in Recommendation Engine.")){
        logger.severe("Error in Recommendation Engine.");
        return;
      }

      System.out.println();
      jsonFormatter.printFormattedRecommendation(response);
    } catch (IOException e) {
      logger.severe("Error getting item count: " + e.getMessage());
    } catch (NullPointerException e) {
      logger.severe("Error getting Recommendation Engine: " + e.getMessage());
    }
  }

  private int getItemCount() throws IOException {
    while (true) {
      int itemCount = InputHandler.getInt("\nEnter the number of items to view (Min 0, Max 5): ");
      if (itemCount > 0 && itemCount < 6) {
        return itemCount;
      } else {
        System.out.println("Invalid input. Please enter a positive number.");
      }
    }
  }
}
