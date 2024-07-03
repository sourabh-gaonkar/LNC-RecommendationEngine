package com.lnc.controller;

import com.lnc.service.UndeliveredNotifications;

public class NotificationRoutes implements RouteHandler {
    private final UndeliveredNotifications newNotification = new UndeliveredNotifications();

    @Override
    public String handle(String path, String data) throws Exception {
        if (path.equals("/getNotifications")) {
            return newNotification.getNewNotifications(data);
        }
        throw new IllegalArgumentException("Invalid path for NotificationRoutes: " + path);
    }
}
