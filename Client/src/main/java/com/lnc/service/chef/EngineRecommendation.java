package com.lnc.service.chef;

import com.lnc.connection.ServerConnection;
import com.lnc.util.JsonDataFormat;

public class EngineRecommendation {
    public void viewRecommendation() throws Exception {
        String apiPath = "/chef/getRecommendation";
        String request = apiPath + "& ";
        String response = ServerConnection.requestServer(request);

        JsonDataFormat jsonFormatter = new JsonDataFormat();
        System.out.println();
        jsonFormatter.printFormattedRecommendation(response);
    }
}
