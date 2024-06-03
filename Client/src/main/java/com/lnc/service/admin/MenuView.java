package com.lnc.service.admin;

import com.lnc.connection.ServerConnection;
import com.lnc.util.JsonDataFormat;

public class MenuView {
    public void displayMenu() throws Exception {
        String apiPath = "/admin/viewItems";
        String request = apiPath + "& ";
        String response = ServerConnection.requestServer(request);

        JsonDataFormat jsonFormatter = new JsonDataFormat();
        System.out.println();
        jsonFormatter.prettyView(response);
    }
}
