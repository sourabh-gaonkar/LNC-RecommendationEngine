package com.lnc.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DAO.UserDetails;
import com.lnc.employee.Employee;
import com.lnc.json.FromJson;

import java.sql.SQLException;

public class Registration {
    private final UserDetails user = new UserDetails();

    public Registration() throws SQLException {
    }

    public String addUser(String jsonData) throws Exception {
        FromJson converter = new FromJson();
        String employeeID = converter.getJsonValue("employeeID", jsonData);
        String name = converter.getJsonValue("name", jsonData);
        String role = converter.getJsonValue("role", jsonData);
        String emailID = converter.getJsonValue("emailID", jsonData);
        String password = converter.getJsonValue("password", jsonData);

        Employee employee = new Employee();
        employee.setEmployeeID(employeeID);
        employee.setName(name);
        employee.setRole(role);
        employee.setEmailID(emailID);
        employee.setPassword(password);

        boolean success = user.addUser(employee);
        if (success) {
            return "Employee added successfully.";
        } else {
            return "Unable to add employee.";
        }
    }
}
