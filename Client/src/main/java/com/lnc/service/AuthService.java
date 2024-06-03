package com.lnc.service;

import com.lnc.connection.ServerConnection;
import com.lnc.controller.AdminController;
import com.lnc.controller.ChefController;
import com.lnc.controller.EmployeeController;
import com.lnc.util.FromJsonConversion;
import com.lnc.util.ToJsonConversion;

public class AuthService {
    ToJsonConversion jsonConversion = new ToJsonConversion();
    public void authenticate(String employeeID, String password) throws Exception {
        String request = jsonConversion.codeLoginCredentials(employeeID, password);
        try {
            String response = ServerConnection.requestServer(request);

            FromJsonConversion jsonDecode = new FromJsonConversion();
            String name = jsonDecode.getJsonValue("name", response);
            String role = jsonDecode.getJsonValue("role", response);

            switch (role) {
                case "ADMIN" -> {
                    AdminController adminController = new AdminController(name, employeeID);
                    adminController.runHomepage();
                }
                case "CHEF" -> {
                    ChefController chefController = new ChefController(name, employeeID);
                    chefController.runHomePage();
                }
                case "EMPLOYEE" -> {
                    EmployeeController employeeController = new EmployeeController(name, employeeID);
                    employeeController.runHomePage();
                }
            }
        } catch (Exception ex){
            System.out.println("Server connection failed.");
        }
    }
}
