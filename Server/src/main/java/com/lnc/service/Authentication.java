package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.UserDetailsQueries;
import com.lnc.DB.UserLoginLogQueries;
import com.lnc.model.Employee;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authentication {
    private final UserDetailsQueries userDetailsQueries = new UserDetailsQueries();
    private final UserLoginLogQueries userLoginLogQueries = new UserLoginLogQueries();
    private final Logger logger = Logger.getLogger(Authentication.class.getName());

    public String authenticate(String jsonData) {
        try {
            String employeeID = extractEmployeeID(jsonData);
            String password = extractPassword(jsonData);

            if (isEmployeeIDValid(employeeID)) {
                return authenticateUser(employeeID, password);
            } else {
                return "EmployeeID does not exist.";
            }
        } catch (JsonProcessingException | NullPointerException e) {
            logger.log(Level.SEVERE, "JSON processing error: ", e);
            return "Wrong request format.";
        }
    }

    private String extractEmployeeID(String jsonData) throws JsonProcessingException, NullPointerException {
        ConversionFromJson fromJsonConverter = new ConversionFromJson();
        return fromJsonConverter.getJsonValue("employeeID", jsonData);
    }

    private String extractPassword(String jsonData) throws JsonProcessingException, NullPointerException {
        ConversionFromJson fromJsonConverter = new ConversionFromJson();
        return fromJsonConverter.getJsonValue("password", jsonData);
    }

    private boolean isEmployeeIDValid(String employeeID) {
        return userDetailsQueries.validateEmployeeID(employeeID);
    }

    public String authenticateUser(String employeeID, String password) throws JsonProcessingException {
        Employee employee = userDetailsQueries.authenticateUser(employeeID, password);
        if(employee == null) {
            return "Wrong username or password.";
        }
        else if (employee.getEmployeeID() != null) {
            if(userLoginLogQueries.addLoginLog(employee.getEmployeeID())) {
                logger.info("Login log added for employee: " + employee.getEmployeeID());
            } else {
                logger.warning("Failed to add login log for employee: " + employee.getEmployeeID());
            }
            return convertEmployeeToJson(employee);
        } else {
            return "Wrong username or password.";
        }
    }

    private String convertEmployeeToJson(Employee employee) throws JsonProcessingException {
        ConversionToJson toJsonConverter = new ConversionToJson();
        return toJsonConverter.codeUserDetails(employee);
    }
}
