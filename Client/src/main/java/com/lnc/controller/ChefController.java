package com.lnc.controller;

import static java.lang.Thread.sleep;

import com.lnc.service.UserLogout;
import com.lnc.service.chef.EngineRecommendation;
import com.lnc.service.chef.FeedbackView;
import com.lnc.service.chef.MenuRollout;
import com.lnc.service.chef.ReportGenerator;
import com.lnc.util.InputHandler;

public class ChefController {
  private final String name;
  private final String employeeID;

  public ChefController(String name, String employeeID) {
    this.name = name;
    this.employeeID = employeeID;
  }

  public void runHomePage() throws Exception {
    System.out.println("\nWelcome " + name);
    run();
  }

  private void run() throws Exception {
    while (true) {
      displayOptions();
      int choice = InputHandler.getInt("Enter your choice: ");
      processOption(choice);
      if (choice == 5) {
        break;
      }
    }
  }

  private void processOption(int choice) throws Exception {
    switch (choice) {
      case 1:
        MenuRollout menuRollout = new MenuRollout();
        menuRollout.rolloutMenu();
        break;
      case 2:
        EngineRecommendation engineRecommendation = new EngineRecommendation();
        engineRecommendation.viewRecommendation();
        break;
      case 3:
        FeedbackView feedbackView = new FeedbackView();
        feedbackView.getFeedbacks();
        break;
      case 4:
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateReport();
        break;
      case 5:
        UserLogout userLogout = new UserLogout();
        boolean isLoggedOut = userLogout.logout(employeeID);
        if (isLoggedOut) {
          System.out.println("Logging out...");
          sleep(5);
        } else {
          choice = 0;
          System.out.println("Error in logging out");
        }
        break;
      default:
        System.out.println("Invalid choice");
        run();
        break;
    }
  }

  private void displayOptions() {
    System.out.println("\n1. Rollout menu for tomorrow.");
    System.out.println("2. View Recommendation from Engine.");
    System.out.println("3. View Feedback");
    System.out.println("4. Generate Report");
    System.out.println("5. Logout");
  }
}
