package com.lnc.controller;

import com.lnc.service.UserLogout;
import com.lnc.service.chef.EngineRecommendation;
import com.lnc.service.chef.FeedbackView;
import com.lnc.service.chef.MenuRollout;
import com.lnc.service.chef.ReportGenerator;
import com.lnc.service.discardedItem.DiscardedItemProcessor;
import com.lnc.service.discardedItem.ImproviseItemFeedbackDisplay;
import com.lnc.util.InputHandler;

import static java.lang.Thread.sleep;

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
      if (choice == 7) {
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
        DiscardedItemProcessor discardedItemProcessor = new DiscardedItemProcessor();
        discardedItemProcessor.processDiscardedItems();
        break;
      case 6:
        ImproviseItemFeedbackDisplay improviseItemFeedbackDisplay = new ImproviseItemFeedbackDisplay();
        improviseItemFeedbackDisplay.displayFeedback();
        break;
      case 7:
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
    System.out.println("5. View Discard Items. (Available 1st of every month)");
    System.out.println("6. View Improvise Item Feedback");
    System.out.println("7. Logout");
  }
}
