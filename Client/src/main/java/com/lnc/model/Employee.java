package com.lnc.model;

public class Employee extends User {
  public Employee() {
    setRole("EMPLOYEE");
  }

  public void setEmployeeID(String employeeID) {
    setUserId(employeeID);
  }
}
