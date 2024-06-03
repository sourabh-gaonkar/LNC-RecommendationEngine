package com.lnc.controller;

import com.lnc.util.InputHandler;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ChefController {
    private final String name;
    private final String employeeID;

    public ChefController(String name, String employeeID) {
        this.name = name;
        this.employeeID = employeeID;
    }

    public void runHomePage() throws IOException, InterruptedException {
        System.out.println("\nWelcome "+ name);
        run();
    }

    private void run() throws IOException, InterruptedException {
        displayOptions();
        int choice = InputHandler.getInt("Enter your choice: ");
        processOption(choice);
    }

    private void processOption(int choice) throws InterruptedException, IOException {
        switch (choice) {
            case 1:
                System.out.println("Rollout Menu for Tomorrow");
                break;
            case 2:
                System.out.println("View Recommendation from Engine.");
                break;
            case 3:
                System.out.println("View FeedBack.");
                break;
            case 4:
                System.out.println("Generate Report.");
                break;
            case 5:
                System.out.println("Logging out...");
                sleep(300);
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
