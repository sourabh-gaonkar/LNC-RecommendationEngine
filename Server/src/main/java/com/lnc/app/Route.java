package com.lnc.app;

import com.lnc.service.NewNotification;
import com.lnc.service.Registration;
import com.lnc.service.admin.MenuItemAddition;
import com.lnc.service.admin.MenuItemDeletion;
import com.lnc.service.admin.MenuItemDisplay;
import com.lnc.service.admin.MenuItemUpdate;
import com.lnc.service.authentication;
import com.lnc.service.chef.FeedbackDisplay;
import com.lnc.service.chef.ReportGenerator;
import com.lnc.service.chef.RolloutMenu;
import com.lnc.service.employee.*;
import com.lnc.service.recommendationEngine.RecommendationEngine;

public class Route {
    private String response = null;

    public String redirect(String request) throws Exception {
        String[] parts = request.split("&");
        String path = parts[0];
        String data = parts[1];

        switch (path) {
            case "/login":
                authentication auth = new authentication();
                response = auth.authenticate(data);
                break;

            case "/register":
                Registration register = new Registration();
                response = register.addUser(data);
                break;

            case "/getNotifications":
                NewNotification newNotification = new NewNotification();
                response = newNotification.getNewNotifications(data);
                break;

            case "/admin/addItem":
                MenuItemAddition addItem = new MenuItemAddition();
                response = addItem.addMenuItem(data);
                break;

            case "/admin/deleteItem":
                MenuItemDeletion deleteItem = new MenuItemDeletion();
                response = deleteItem.deleteMenuItem(data);
                break;

            case "/admin/viewItems":
                MenuItemDisplay viewItems = new MenuItemDisplay();
                response = viewItems.displayMenu();
                break;

            case "/admin/updateItem":
                MenuItemUpdate updateItem = new MenuItemUpdate();
                response = updateItem.updateMenuItem(data);
                break;

            case "/employee/feedback":
                EmployeeFeedback employeeFeedback = new EmployeeFeedback();
                response = employeeFeedback.getEmployeeFeedback(data);
                break;

            case "/employee/getNotifications":
                AllNotificationsOfEmployee allNotificationsOfEmployee = new AllNotificationsOfEmployee();
                response = allNotificationsOfEmployee.getAllNotificationsOfEmployee(data);
                break;

            case "/employee/todaysMenu":
                TodaysMenu todaysMenu = new TodaysMenu();
                response = todaysMenu.getTodaysMenu();
                break;

            case "/employee/tomorrowsMenu":
                TomorrowsMenu tomorrowsMenu = new TomorrowsMenu();
                response = tomorrowsMenu.getTomorrowsMenu();
                break;

            case "/employee/vote":
                MenuVote menuVote = new MenuVote();
                response = menuVote.voteForMenu(data);
                break;

            case "/chef/getFeedback":
                FeedbackDisplay feedbackDisplay = new FeedbackDisplay();
                response = feedbackDisplay.displayFeedback(data);
                break;

            case "/chef/getRecommendation":
                RecommendationEngine recommendationEngine = new RecommendationEngine();
                response = recommendationEngine.runEngine();
                break;

            case "/chef/rolloutMenu":
                RolloutMenu rolloutMenu = new RolloutMenu();
                response = rolloutMenu.rolloutMenu(data);
                break;

            case "/chef/generateReport":
                ReportGenerator reportGenerator = new ReportGenerator();
                response = reportGenerator.generateReport(data);
                break;

            default:
                throw new IllegalArgumentException("Invalid path: " + path);
        }

        System.out.println("Response: " + response);
        return response;
    }
}
