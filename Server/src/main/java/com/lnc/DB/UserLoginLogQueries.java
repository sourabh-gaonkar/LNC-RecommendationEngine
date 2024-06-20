package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserLoginLogQueries {
    private final Logger logger = Logger.getLogger(UserLoginLogQueries.class.getName());
    private Connection connection;

    public UserLoginLogQueries() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException ex) {
            logger.severe("Failed to connect to database: " + ex.getMessage());
        }
    }

    public boolean addLoginLog(String employeeID) {
        boolean isLoginLogAdded = false;
        String query = "INSERT INTO user_login_log (employee_id, action) VALUES (?, 'LOGIN')";

        try(PreparedStatement addLoginLogStmt = connection.prepareStatement(query)) {
            addLoginLogStmt.setString(1, employeeID);
            isLoginLogAdded = addLoginLogStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.severe("Failed to add login log: " + ex.getMessage());
        }
        return isLoginLogAdded;
    }

    public boolean addLogoutLog(String employeeID) {
        boolean isLogoutLogAdded = false;
        String query = "INSERT INTO user_login_log (employee_id, action) VALUES (?, 'LOGOUT')";

        try(PreparedStatement addLogoutLogStmt = connection.prepareStatement(query)) {
            addLogoutLogStmt.setString(1, employeeID);
            isLogoutLogAdded = addLogoutLogStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.severe("Failed to add logout log: " + ex.getMessage());
        }
        return isLogoutLogAdded;
    }
}
