package com.lnc.service;

import com.lnc.DB.UserDetails;
import com.lnc.model.Employee;
import com.lnc.utils.FromJson;
import com.lnc.utils.ToJson;

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
