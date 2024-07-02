package com.lnc.controller;

import com.lnc.service.Authentication;
import com.lnc.service.LogoutUser;

public class AuthRoutes implements RouteHandler {
    private final Authentication auth = new Authentication();
    private final LogoutUser logoutUser = new LogoutUser();

    @Override
    public String handle(String path, String data) throws Exception {
        return switch (path) {
            case "/login" -> auth.authenticate(data);
            case "/logout" -> logoutUser.logout(data);
            default -> throw new IllegalArgumentException("Invalid path for AuthRoutes: " + path);
        };
    }
}

