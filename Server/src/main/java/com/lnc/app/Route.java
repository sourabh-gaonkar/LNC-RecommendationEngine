package com.lnc.app;

import com.lnc.service.Authentication;
import com.lnc.service.LogoutUser;
import com.lnc.service.NewNotification;
import com.lnc.service.Registration;
import com.lnc.service.admin.MenuItemAddition;
import com.lnc.service.admin.MenuItemDeletion;
import com.lnc.service.admin.MenuItemDisplay;
import com.lnc.service.admin.MenuItemUpdate;
import com.lnc.service.chef.FeedbackDisplay;
import com.lnc.service.chef.ReportGenerator;
import com.lnc.service.chef.RolloutMenu;
import com.lnc.service.discardItem.DiscardItemDeletion;
import com.lnc.service.discardItem.ItemDiscard;
import com.lnc.service.employee.*;
import com.lnc.service.recommendationEngine.RecommendationEngine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Route {
    private final Logger logger = Logger.getLogger(Route.class.getName());

    public String redirect(String request) throws Exception {
        String[] parts = request.split("&");
        String path = parts[0];
        String data = parts[1];

        String response = null;
        switch (path) {
            case "/login":
                Authentication auth = new Authentication();
                response = auth.authenticate(data);
                break;

            case "/logout":
                LogoutUser logoutUser = new LogoutUser();
                response = logoutUser.logout(data);
                break;


            case "/register":
                Registration register = new Registration();
                response = register.addUser(data);
                break;

            case "/getNotifications":
                NewNotification newNotification = new NewNotification();
                response = newNotification.getNewNotifications(data);
                break;

            case "/getDiscardItems":
                ItemDiscard itemDiscard = new ItemDiscard();
                response = itemDiscard.getDiscardItemList();
                break;

            case "/discardItem/deleteItem":
                DiscardItemDeletion discardItemDeletion = new DiscardItemDeletion();
                response = discardItemDeletion.deleteDiscardedItem(data);
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
                response = recommendationEngine.runEngine(data);
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
                response = "Invalid Server call.";
                throw new IllegalArgumentException("Invalid path: " + path);
        }

        logger.log(Level.INFO, "Response: {0} ", response);
        return response;
    }
}
