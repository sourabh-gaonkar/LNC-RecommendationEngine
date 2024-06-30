package com.lnc.service.employee;

import com.lnc.connection.ServerConnection;
import com.lnc.util.JsonDataFormat;
import com.lnc.util.ToJsonConversion;

public class TodaysMenu {
  public void viewTodaysMenu(String employeeId) throws Exception {
    String apiPath = "/employee/todaysMenu";

    ToJsonConversion toJsonConversion = new ToJsonConversion();
    String jsonData = toJsonConversion.codeEmployeeID(employeeId);

    String request = apiPath + "&" + jsonData;

    String response = ServerConnection.requestServer(request);

    JsonDataFormat jsonDataFormat = new JsonDataFormat();
    jsonDataFormat.printDaysMenu(response, "TODAY");
  }
}
