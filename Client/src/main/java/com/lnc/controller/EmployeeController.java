package com.lnc.controller;

import static java.lang.Thread.sleep;

import com.lnc.service.NotificationService;
import com.lnc.service.employee.AllNotificationsOfEmployee;
import com.lnc.service.employee.EmployeeFeedback;
import com.lnc.service.employee.TodaysMenu;
import com.lnc.service.employee.TomorrowsMenuVoting;
import com.lnc.util.InputHandler;
import java.awt.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EmployeeController {
    private final ZonedDateTime nowIST = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
    private final LocalTime votingStartTime = LocalTime.of(10, 0);
    private final LocalTime votingEndTime = LocalTime.of(17, 30);
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
    NotificationService newNotifications = new NotificationService();
        newNotifications.fetchAndDisplayNotifications(employeeID);
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
                TodaysMenu todaysMenu = new TodaysMenu();
                todaysMenu.viewTodaysMenu();
                break;
            case 2:
                LocalTime currentTime = nowIST.toLocalTime();
//                if (currentTime.isBefore(votingStartTime) || currentTime.isAfter(votingEndTime)) {
//                    System.out.println("Voting is closed. Please try again tomorrow.");
//                    break;
//                }
                TomorrowsMenuVoting tomorrowsMenu = new TomorrowsMenuVoting();
                tomorrowsMenu.voteForTomorrowsMenu(employeeID);
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
        System.out.println("2. Select tomorrow's menu. (Voting time: 10:00 AM to 5:30 PM)");
        System.out.println("3. Give Feedback.");
        System.out.println("4. View Notifications.");
        System.out.println("5. Logout");
    }
}
