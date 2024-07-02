package com.lnc.controller;

import com.lnc.service.NewNotification;

public class NotificationRoutes implements RouteHandler {
    private final NewNotification newNotification = new NewNotification();

    @Override
    public String handle(String path, String data) throws Exception {
        if (path.equals("/getNotifications")) {
            return newNotification.getNewNotifications(data);
        }
        throw new IllegalArgumentException("Invalid path for NotificationRoutes: " + path);
    }
}
