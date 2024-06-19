package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserDetailsQueries {
    Logger logger = Logger.getLogger(UserDetailsQueries.class.getName());
    private Connection connection;

    public UserDetailsQueries() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException ex) {
            logger.severe("Failed to establish connection to the database.\n" + ex.getMessage());
        }
    }

        public boolean addUser(Employee employee) {
        boolean isUserAdded = false;

        String query = "INSERT INTO user_details VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement addUserStmt = connection.prepareStatement(query)) {
            addUserStmt.setString(1, employee.getEmployeeID());
            addUserStmt.setString(2, employee.getName());
            addUserStmt.setString(3, employee.getRole());
            addUserStmt.setString(4, employee.getEmailID());
            addUserStmt.setString(5, employee.getPassword());

            int rowsAffected = addUserStmt.executeUpdate();
            if(rowsAffected > 0) {
                isUserAdded = true;
            }
        } catch (SQLException ex) {
            logger.severe("Failed to add user.\n" + ex.getMessage());
        }
        return isUserAdded;
    }

    public boolean validateEmployeeID(String employeeID) {
        boolean isEmployeeIDValid = false;

        String query = "SELECT * FROM user_details WHERE employee_id =?";

        try(PreparedStatement validateEmployeeIDStmt = connection.prepareStatement(query)) {
            validateEmployeeIDStmt.setString(1, employeeID);

            ResultSet rs = validateEmployeeIDStmt.executeQuery();
            if(rs.next()) {
                isEmployeeIDValid = true;
            }
        } catch (SQLException ex) {
            logger.severe("Failed to validate employee ID.\n" + ex.getMessage());
        }
        return isEmployeeIDValid;
    }

    public Employee authenticateUser(String employeeID, String password) {
        Employee employee = new Employee();
        String actualPassword = null;

        String query = "SELECT * FROM user_details WHERE employee_id =?";

        try(PreparedStatement authenticateUserStmt = connection.prepareStatement(query)) {
            authenticateUserStmt.setString(1, employeeID);

            ResultSet rs = authenticateUserStmt.executeQuery();
            if(rs.next()) {
                actualPassword = rs.getString("password");
                employee.setEmployeeID(employeeID);
                employee.setName(rs.getString("name"));
                employee.setRole(rs.getString("role"));
                employee.setEmailID(rs.getString("email"));
                employee.setPassword(null);
                if(!password.equals(actualPassword)) {
                    return null;
                }
            }
        } catch (SQLException ex) {
            logger.severe("Failed to authenticate user.\n" + ex.getMessage());
        }

        return employee;
    }

    public String getUserName(String employeeID) {
        String name = null;

        String query = "SELECT * FROM user_details WHERE employee_id =?";

        try(PreparedStatement getUserNameStmt = connection.prepareStatement(query)) {
            getUserNameStmt.setString(1, employeeID);

            ResultSet rs = getUserNameStmt.executeQuery();
            if(rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException ex) {
            logger.severe("Failed to get user name.\n" + ex.getMessage());
        }
        return name;
    }
}
