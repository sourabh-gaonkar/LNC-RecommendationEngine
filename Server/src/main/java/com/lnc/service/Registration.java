package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.UserDetailsQueries;
import com.lnc.model.Employee;
import com.lnc.utils.ConversionFromJson;
import java.sql.SQLException;

public class Registration {
  private final UserDetailsQueries user = new UserDetailsQueries();

  public Registration() {}

  public String addUser(String jsonData) {
    try{
      Employee employee = extractJsonData(jsonData);

      boolean success = user.addUser(employee);
      if (success) {
        return "Employee added successfully.";
      } else {
        return "Unable to add employee.";
      }
    } catch (JsonProcessingException | NullPointerException e) {
      return "Invalid data format.";
    }
  }

  private Employee extractJsonData(String jsonData) throws JsonProcessingException, NullPointerException {
    ConversionFromJson converter = new ConversionFromJson();
    String employeeID = converter.getJsonValue("employeeID", jsonData);
    String name = converter.getJsonValue("name", jsonData);
    String role = converter.getJsonValue("role", jsonData);
    String emailID = converter.getJsonValue("email", jsonData);
    String password = converter.getJsonValue("password", jsonData);

    Employee employee = new Employee();
    employee.setEmployeeID(employeeID);
    employee.setName(name);
    employee.setRole(role);
    employee.setEmailID(emailID);
    employee.setPassword(password);
    return employee;
  }
}
