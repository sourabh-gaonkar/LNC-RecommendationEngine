package com.lnc.controller;

import com.lnc.service.regiteration.Registration;
import com.lnc.util.InputHandler;

import static java.lang.Thread.sleep;

public class HomeController {
  Registration register = new Registration();
  LoginController loginController = new LoginController();

  public void runHomePage() throws Exception {
    displayOptions();
    int choice = InputHandler.getInt("Enter your choice: ");
    processOption(choice);
  }

  private void displayOptions() {
    System.out.println("\n1. Login");
    System.out.println("2. Register");
    System.out.println("3. Exit");
  }

  private void processOption(int choice) throws Exception {
    switch (choice) {
      case 1:
        loginController.loginUser();
        runHomePage();
        break;
      case 2:
        register.registerUser();
        runHomePage();
        break;
      case 3:
        System.out.println("Exiting...");
        InputHandler.closeInputHandler();
        sleep(300);
        break;
      default:
        System.out.println("Invalid choice\n");
        runHomePage();
        break;
    }
  }
}
