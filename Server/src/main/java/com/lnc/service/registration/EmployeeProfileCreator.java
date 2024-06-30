package com.lnc.service.registration;

import com.lnc.DB.EmployeeProfileQueries;
import com.lnc.model.EmployeeProfile;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Logger;

public class EmployeeProfileCreator {
    private final Logger logger = Logger.getLogger(EmployeeProfileCreator.class.getName());
    private final ConversionFromJson converter = new ConversionFromJson();
    private final EmployeeProfileQueries employeeProfileQueries = new EmployeeProfileQueries();
    public String createEmployeeProfile(String jsonData) {
        try {
            EmployeeProfile employeeProfile = converter.decodeEmployeeProfile(jsonData);
            if(employeeProfileQueries.addEmployeePreferences(employeeProfile)) {
                return "Employee profile created successfully.";
            }
            return "Failed to create employee profile.";
        } catch (Exception e) {
            logger.severe("Error processing employee profile creation: " + e.getMessage());
            return "Error processing employee profile creation.";
        }
    }
}
