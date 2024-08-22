package com.lnc.service.employee;

import com.lnc.connection.ServerConnection;
import com.lnc.model.EmployeeProfile;
import com.lnc.service.regiteration.EmployeeProfileFetcher;
import com.lnc.util.JsonStringConverter;

import java.io.IOException;

public class EmployeeProfileUpdater {
    public void updateEmployeeProfile(String employeeId) throws IOException {
        EmployeeProfileFetcher employeeProfileFetcher = new EmployeeProfileFetcher();
        EmployeeProfile employeeProfile = employeeProfileFetcher.getEmployeeProfile(employeeId);

        JsonStringConverter converter = new JsonStringConverter();
        String request = converter.codeUserPreference(employeeProfile, "/employee/editProfile");

        String response = ServerConnection.requestServer(request);
        System.out.println("Response: " + response);
    }
}
