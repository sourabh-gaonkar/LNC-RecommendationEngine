package com.lnc.service.employee;

import com.lnc.connection.ServerConnection;
import com.lnc.util.JsonDataFormat;
import com.lnc.util.ToJsonConversion;

public class AllNotificationsOfEmployee {
  public void getAllNotifications(String employeeID) throws Exception {
    ToJsonConversion convertToJson = new ToJsonConversion();
    String jsonsString = convertToJson.codeEmployeeID(employeeID);

    String apiPath = "/employee/getNotifications";
    String request = apiPath + "&" + jsonsString;

    String response = ServerConnection.requestServer(request);
    if(!validateResponse(response)){
      System.out.println("Error fetching notifications. Try later.");
      return;
    }

    JsonDataFormat formatter = new JsonDataFormat();
    formatter.printAllNotifications(response);
  }

  public boolean validateResponse(String response) {
      return !response.equalsIgnoreCase("Error in fetching notifications");
  }
}
