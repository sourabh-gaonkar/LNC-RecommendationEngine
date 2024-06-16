package com.lnc.service.employee;

import com.lnc.connection.ServerConnection;
import com.lnc.util.JsonDataFormat;

public class TodaysMenu {
    public void viewTodaysMenu() throws Exception {
        String apiPath = "/employee/todaysMenu";
        String request = apiPath + "& ";

        String response = ServerConnection.requestServer(request);

        JsonDataFormat jsonDataFormat = new JsonDataFormat();
        jsonDataFormat.printDaysMenu(response, "TODAY");
    }
}
