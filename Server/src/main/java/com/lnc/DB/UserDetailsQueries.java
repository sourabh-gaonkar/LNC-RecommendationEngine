package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserDetailsQueries {
    private static final Logger logger = Logger.getLogger(UserDetailsQueries.class.getName());
    private Connection connection;

    public UserDetailsQueries() {
        try {
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException ex) {
            logger.severe("Failed to establish connection to the database.\n" + ex.getMessage());
        }
    }

    public boolean addUser(Employee employee) {
        String query = "INSERT INTO user_details VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement addUserStmt = connection.prepareStatement(query)) {
            addUserStmt.setString(1, employee.getEmployeeID());
            addUserStmt.setString(2, employee.getName());
            addUserStmt.setString(3, employee.getRole());
            addUserStmt.setString(4, employee.getEmailID());
            addUserStmt.setString(5, employee.getPassword());

            int rowsAffected = addUserStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            logger.severe("Failed to add user.\n" + ex.getMessage());
            return false;
        }
    }

    public boolean validateEmployeeID(String employeeID) {
        String query = "SELECT * FROM user_details WHERE employee_id =?";

        try (PreparedStatement validateEmployeeIDStmt = connection.prepareStatement(query)) {
            validateEmployeeIDStmt.setString(1, employeeID);

            try (ResultSet rs = validateEmployeeIDStmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            logger.severe("Failed to validate employee ID.\n" + ex.getMessage());
            return false;
        }
    }

    public Employee authenticateUser(String employeeID, String password) {
        String query = "SELECT * FROM user_details WHERE employee_id =?";

        try (PreparedStatement authenticateUserStmt = connection.prepareStatement(query)) {
            authenticateUserStmt.setString(1, employeeID);

            try (ResultSet rs = authenticateUserStmt.executeQuery()) {
                if (rs.next()) {
                    String actualPassword = rs.getString("password");
                    if (password.equals(actualPassword)) {
                        Employee employee = new Employee();
                        employee.setEmployeeID(employeeID);
                        employee.setName(rs.getString("name"));
                        employee.setRole(rs.getString("role"));
                        employee.setEmailID(rs.getString("email"));
                        return employee;
                    }
                }
            }
        } catch (SQLException ex) {
            logger.severe("Failed to authenticate user.\n" + ex.getMessage());
        }

        return null;
    }

    public String getUserName(String employeeID) {
        String query = "SELECT * FROM user_details WHERE employee_id =?";

        try (PreparedStatement getUserNameStmt = connection.prepareStatement(query)) {
            getUserNameStmt.setString(1, employeeID);

            try (ResultSet rs = getUserNameStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException ex) {
            logger.severe("Failed to get user name.\n" + ex.getMessage());
        }

        return null;
    }

    // Helper method to handle ResultSet closing
    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.severe("Failed to close ResultSet.\n" + ex.getMessage());
            }
        }
    }
}
