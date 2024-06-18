package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.UserDetailsQueries;
import com.lnc.model.Employee;
import com.lnc.utils.FromJson;
import com.lnc.utils.ToJson;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Authentication {
  private final UserDetailsQueries user = new UserDetailsQueries();
  private final Logger logger = Logger.getLogger(Authentication.class.getName());

  public Authentication() throws SQLException {}

  public String authenticate(String jsonData) {
    try {
      FromJson converter = new FromJson();
      String employeeID = converter.getJsonValue("employeeID", jsonData);
      String password = converter.getJsonValue("password", jsonData);

      if (user.validateEmployeeID(employeeID)) {
        Employee employee = user.authenticateUser(employeeID, password);
        if (employee != null) {
          ToJson json = new ToJson();
          return json.codeUserDetails(employee);
        } else {
          return "Wrong username, password.";
        }
      }
      return "EmployeeID does not exist.";
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "Wrong request format.";
    }
  }
}
