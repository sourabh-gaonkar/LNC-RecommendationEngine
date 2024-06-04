package com.lnc.app;

import com.lnc.admin.*;
import com.lnc.auth.Registration;
import com.lnc.auth.authentication;
import com.lnc.chef.FeedbackDisplay;
import com.lnc.employee.EmployeeFeedback;

public class Redirection {
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

            case "/chef/getFeedback":
                FeedbackDisplay feedbackDisplay = new FeedbackDisplay();
                response = feedbackDisplay.displayFeedback(data);
                break;

            default:
                throw new IllegalArgumentException("Invalid path: " + path);
        }

        System.out.println("Response: " + response);
        return response;
    }
}
