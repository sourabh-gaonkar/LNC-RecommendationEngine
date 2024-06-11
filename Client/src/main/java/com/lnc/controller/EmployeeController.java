package com.lnc.controller;

import static java.lang.Thread.sleep;

import com.lnc.service.AllNewNotifications;
import com.lnc.service.employee.AllNotificationsOfEmployee;
import com.lnc.service.employee.EmployeeFeedback;
import com.lnc.util.InputHandler;

public class EmployeeController {
    private final String name;
    private final String employeeID;

    public EmployeeController(String name, String employeeID) {
        this.name = name;
        this.employeeID = employeeID;
    }

  public void runHomePage() throws Exception {
        System.out.println("\nWelcome "+ name+ "!");
        System.out.println();
        getNotifications();
        run();
    }

    private void getNotifications() throws Exception {
    AllNewNotifications newNotifications = new AllNewNotifications();
        newNotifications.getNewNotifications(employeeID);
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
                AllNotificationsOfEmployee allNotificationsOfEmployee = new AllNotificationsOfEmployee();
                allNotificationsOfEmployee.getAllNotifications(employeeID);
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
