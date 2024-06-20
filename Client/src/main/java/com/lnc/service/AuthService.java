package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.controller.AdminController;
import com.lnc.controller.ChefController;
import com.lnc.controller.EmployeeController;
import com.lnc.util.FromJsonConversion;
import com.lnc.util.ToJsonConversion;

public class AuthService {
  private final ToJsonConversion jsonConversion = new ToJsonConversion();

  public void authenticate(String employeeID, String password) throws JsonProcessingException {
    String request = jsonConversion.codeLoginCredentials(employeeID, password);
    try {
      String response = ServerConnection.requestServer(request);
      handleServerResponse(response, employeeID);
    } catch (Exception ex) {
      handleServerConnectionFailure();
    }
  }

  private void handleServerResponse(String response, String employeeID) throws Exception {
    switch (response) {
      case "Wrong username or password.":
      case "EmployeeID does not exist.":
      case "Wrong request format.":
        System.out.println("Wrong username or password.");
        break;
      default:
        processValidResponse(response, employeeID);
        break;
    }
  }

  private void processValidResponse(String response, String employeeID) throws Exception {
    FromJsonConversion jsonDecode = new FromJsonConversion();
    String name = jsonDecode.getJsonValue("name", response);
    String role = jsonDecode.getJsonValue("role", response);

    switch (role) {
      case "ADMIN":
        handleAdminLogin(name, employeeID);
        break;
      case "CHEF":
        handleChefLogin(name, employeeID);
        break;
      case "EMPLOYEE":
        handleEmployeeLogin(name, employeeID);
        break;
      default:
        System.out.println("Unknown role received.");
        break;
    }
  }

  private void handleAdminLogin(String name, String employeeID) throws Exception {
    AdminController adminController = new AdminController(name, employeeID);
    adminController.runHomepage();
  }

  private void handleChefLogin(String name, String employeeID) throws Exception {
    ChefController chefController = new ChefController(name, employeeID);
    chefController.runHomePage();
  }

  private void handleEmployeeLogin(String name, String employeeID) throws Exception {
    EmployeeController employeeController = new EmployeeController(name, employeeID);
    employeeController.runHomePage();
  }

  private void handleServerConnectionFailure() {
    System.out.println("Server connection failed.");
  }
}
