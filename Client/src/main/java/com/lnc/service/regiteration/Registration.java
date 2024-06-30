package com.lnc.service.regiteration;

import com.lnc.connection.ServerConnection;
import com.lnc.model.Employee;
import com.lnc.model.EmployeeProfile;
import com.lnc.util.ToJsonConversion;

public class Registration {
    private final ToJsonConversion converter = new ToJsonConversion();
    public void registerUser() throws Exception {
        EmployeeDetailsFetcher employeeDetailsFetcher = new EmployeeDetailsFetcher();
        Employee employee = employeeDetailsFetcher.getEmployee();

        String requestUserCreation = converter.codeUserDetails(employee);

        String responseUserCreation = ServerConnection.requestServer(requestUserCreation);
        System.out.println("Response: " + responseUserCreation);

        if (responseUserCreation.equalsIgnoreCase("Employee added successfully.")) {
            EmployeeProfileFetcher employeeProfileFetcher = new EmployeeProfileFetcher();
            EmployeeProfile employeeProfile = employeeProfileFetcher.getEmployeeProfile(employee.getEmployeeID());

            String requestUserPreference = converter.codeUserPreference(employeeProfile, "/register/userPreference");

            String responseUserPreference = ServerConnection.requestServer(requestUserPreference);
            System.out.println("Response: " + responseUserPreference);
        }
    }
}
