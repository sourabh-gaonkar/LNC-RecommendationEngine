package com.lnc.controller;

import com.lnc.service.employee.EmployeeFeedback;
import com.lnc.util.InputHandler;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class EmployeeController {
    private final String name;
    private final String employeeID;

    public EmployeeController(String name, String employeeID) {
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
                System.out.println("View today's menu.");
                break;
            case 2:
                System.out.println("Select tomorrow's menu.");
                break;
            case 3:
                EmployeeFeedback employeeFeedback = new EmployeeFeedback();
                employeeFeedback.takeEmployeeFeedback(employeeID);
                break;
            case 4:
                System.out.println("View Notifications.");
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
        System.out.println("\n1. View today's menu.");
        System.out.println("2. Select tomorrow's menu.");
        System.out.println("3. Give Feedback.");
        System.out.println("4. View Notifications.");
        System.out.println("5. Logout");
    }
}
