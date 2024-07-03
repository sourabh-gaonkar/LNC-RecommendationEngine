package com.lnc.service;

import com.lnc.DB.UserLoginLogQueries;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Logger;

public class UserLogout {
    private final Logger logger = Logger.getLogger(UserLogout.class.getName());
    private final UserLoginLogQueries userLoginLogQueries = new UserLoginLogQueries();
    private final ConversionFromJson fromJsonConverter = new ConversionFromJson();

    public String logout(String jsonData) {
        try {
            String employeeID = fromJsonConverter.getJsonValue("employee_id", jsonData);

            if (userLoginLogQueries.addLogoutLog(employeeID)) {
                logger.info("Logout log added for employee: " + employeeID);
            } else {
                logger.warning("Failed to add logout log for employee: " + employeeID);
            }
            return "User logged out.";
        } catch (Exception e) {
            logger.severe("Failed to logout user: " + e.getMessage());
            return "Failed to logout user.";
        }
    }
}
