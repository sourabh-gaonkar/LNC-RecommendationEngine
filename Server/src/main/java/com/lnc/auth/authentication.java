package com.lnc.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DAO.UserDetails;
import com.lnc.employee.Employee;
import com.lnc.json.FromJson;
import com.lnc.json.ToJson;

import java.sql.SQLException;

public class authentication {
    private final UserDetails user = new UserDetails();

    public authentication() throws SQLException {
    }

    public String authenticate(String jsonData) throws Exception {
        FromJson converter = new FromJson();
        String employeeID = converter.getJsonValue("employeeID", jsonData);
        String password = converter.getJsonValue("password", jsonData);

        if(user.validateEmployeeID(employeeID)) {
            Employee employee = user.authenticateUser(employeeID, password);
            if(employee != null) {
                ToJson json = new ToJson();
                return json.codeUserDetails(employee);
            } else {
                return "Wrong username, password.";
            }
        }
        return "EmployeeID does not exist.";
    }
}
