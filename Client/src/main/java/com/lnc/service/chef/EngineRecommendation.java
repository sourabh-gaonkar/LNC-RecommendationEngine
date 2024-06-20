package com.lnc.service.chef;

import com.lnc.connection.ServerConnection;
import com.lnc.util.JsonDataFormat;

import java.util.logging.Logger;

public class EngineRecommendation {
  private final Logger logger = Logger.getLogger(EngineRecommendation.class.getName());
  public void viewRecommendation() {
    String apiPath = "/chef/getRecommendation";
    String request = apiPath + "& ";
    String response = ServerConnection.requestServer(request);
    if(response.equals("Error in Recommendation Engine.")){
        logger.severe("Error in Recommendation Engine.");
        return;
    }

    JsonDataFormat jsonFormatter = new JsonDataFormat();
    System.out.println();
    jsonFormatter.printFormattedRecommendation(response);
  }
}
