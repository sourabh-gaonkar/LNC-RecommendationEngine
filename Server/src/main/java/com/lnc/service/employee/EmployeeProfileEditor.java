package com.lnc.service.employee;

import com.lnc.DB.EmployeeProfileQueries;
import com.lnc.model.EmployeeProfile;
import com.lnc.service.registration.EmployeeProfileCreator;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Logger;

public class EmployeeProfileEditor {
    private final Logger logger = Logger.getLogger(EmployeeProfileCreator.class.getName());
    private final ConversionFromJson converter = new ConversionFromJson();
    private final EmployeeProfileQueries employeeProfileQueries = new EmployeeProfileQueries();

    public String editEmployeeProfile(String jsonData) {
        try {
            EmployeeProfile employeeProfile = converter.decodeEmployeeProfile(jsonData);

            if(employeeProfileQueries.updateEmployeePreferences(employeeProfile)) {
                return "Employee profile updated successfully.";
            }
            return "Failed to update employee profile.";
        } catch (Exception e) {
            logger.severe("Error processing employee profile inundation: " + e.getMessage());
            return "Error processing employee profile update.";
        }
    }
}
