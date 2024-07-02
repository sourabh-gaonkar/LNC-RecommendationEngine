package com.lnc.controller;

import com.lnc.service.registration.EmployeeProfileCreator;
import com.lnc.service.registration.Registration;

public class RegistrationRoutes implements RouteHandler {
    private final Registration register = new Registration();
    private final EmployeeProfileCreator employeeProfileCreator = new EmployeeProfileCreator();

    @Override
    public String handle(String path, String data) throws Exception {
        return switch (path) {
            case "/register" -> register.addUser(data);
            case "/register/userPreference" -> employeeProfileCreator.createEmployeeProfile(data);
            default -> throw new IllegalArgumentException("Invalid path for RegistrationRoutes: " + path);
        };
    }
}
