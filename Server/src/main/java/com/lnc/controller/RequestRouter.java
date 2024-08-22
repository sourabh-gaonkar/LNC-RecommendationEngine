package com.lnc.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestRouter {
    private final Logger logger = Logger.getLogger(RequestRouter.class.getName());
    private final Map<String, RouteHandler> routeHandlers = new HashMap<>();

    public RequestRouter() {
        routeHandlers.put("/login", new AuthRoutes());
        routeHandlers.put("/logout", new AuthRoutes());
        routeHandlers.put("/register", new RegistrationRoutes());
        routeHandlers.put("/register/userPreference", new RegistrationRoutes());
        routeHandlers.put("/getNotifications", new NotificationRoutes());
        routeHandlers.put("/getDiscardItems", new DiscardItemRoutes());
        routeHandlers.put("/discardItem/deleteItem", new DiscardItemRoutes());
        routeHandlers.put("/discardItem/addQuestions", new DiscardItemRoutes());
        routeHandlers.put("/discardItem/viewImproviseItem", new DiscardItemRoutes());
        routeHandlers.put("/discardItem/getQuestions", new DiscardItemRoutes());
        routeHandlers.put("/discardItem/submitAnswers", new DiscardItemRoutes());
        routeHandlers.put("/discardItem/getFeedback", new DiscardItemRoutes());
        routeHandlers.put("/admin/addItem", new AdminRequestHandler());
        routeHandlers.put("/admin/addItemProfile", new AdminRequestHandler());
        routeHandlers.put("/admin/updateItemProfile", new AdminRequestHandler());
        routeHandlers.put("/admin/deleteItem", new AdminRequestHandler());
        routeHandlers.put("/admin/viewItems", new AdminRequestHandler());
        routeHandlers.put("/admin/updateItem", new AdminRequestHandler());
        routeHandlers.put("/employee/feedback", new EmployeeRoutes());
        routeHandlers.put("/employee/getNotifications", new EmployeeRoutes());
        routeHandlers.put("/employee/todaysMenu", new EmployeeRoutes());
        routeHandlers.put("/employee/tomorrowsMenu", new EmployeeRoutes());
        routeHandlers.put("/employee/vote", new EmployeeRoutes());
        routeHandlers.put("/employee/editProfile", new EmployeeRoutes());
        routeHandlers.put("/employee/viewImproviseItem", new EmployeeRoutes());
        routeHandlers.put("/employee/getQuestions", new EmployeeRoutes());
        routeHandlers.put("/employee/submitAnswers", new EmployeeRoutes());
        routeHandlers.put("/chef/getFeedback", new ChefRoutes());
        routeHandlers.put("/chef/getRecommendation", new ChefRoutes());
        routeHandlers.put("/chef/rolloutMenu", new ChefRoutes());
        routeHandlers.put("/chef/generateReport", new ChefRoutes());
        // Add other route mappings here...
    }

    public String redirect(String request) throws Exception {
        String[] parts = request.split("&");
        String path = parts[0];
        String data = parts[1];

        RouteHandler handler = routeHandlers.get(path);
        if (handler == null) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }

        String response = handler.handle(path, data);
        logger.log(Level.INFO, "Response: {0} ", response);
        return response;
    }
}
