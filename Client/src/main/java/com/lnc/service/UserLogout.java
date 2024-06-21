package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.util.ToJsonConversion;
import org.apache.commons.lang3.ObjectUtils;

import java.util.logging.Logger;

public class UserLogout {
    private final Logger logger = Logger.getLogger(UserLogout.class.getName());
    public boolean logout(String employeeID) {
        try{
            String apiPath = "/logout";
            String request = apiPath + "&" + new ToJsonConversion().codeEmployeeID(employeeID);
            String response = ServerConnection.requestServer(request);
            return !response.equals("Failed to logout user.") || !ObjectUtils.isEmpty(response);
        } catch (JsonProcessingException | NullPointerException e) {
            logger.severe("Failed to logout user: " + e.getMessage());
            return false;
        }
    }
}
