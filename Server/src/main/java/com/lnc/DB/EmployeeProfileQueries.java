package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.EmployeeProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeProfileQueries {
    private static final Logger logger = Logger.getLogger(EmployeeProfileQueries.class.getName());
    private Connection connection;

    public EmployeeProfileQueries() {
        try {
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to connect to the database.", e);
        }
    }

    public Map<String, Object> getEmployeePreferences(String employeeId) {
        Map<String, Object> preferences = new HashMap<>();
        String query = "SELECT diet_preference, spice_level, regional_preference, sweet_tooth " +
                "FROM employee_profile WHERE employee_id = ?";

        try (PreparedStatement getPreferenceStmt = connection.prepareStatement(query)) {
            getPreferenceStmt.setString(1, employeeId);

            try (ResultSet rs = getPreferenceStmt.executeQuery()) {
                if (rs.next()) {
                    preferences.put("diet_preference", rs.getString("diet_preference"));
                    preferences.put("spice_level", rs.getString("spice_level"));
                    preferences.put("regional_preference", rs.getString("regional_preference"));
                    preferences.put("sweet_tooth", rs.getBoolean("sweet_tooth"));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get employee preferences.", e);
        }

        return preferences;
    }

    public boolean addEmployeePreferences(EmployeeProfile employeeProfile) {
        String query = "INSERT INTO employee_profile (employee_id, diet_preference, spice_level, regional_preference, sweet_tooth) " +
                "VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(query, employeeProfile);
    }

    public boolean updateEmployeePreferences(EmployeeProfile employeeProfile) {
        String query = "UPDATE employee_profile SET diet_preference = ?, spice_level = ?, regional_preference = ?, sweet_tooth = ? " +
                "WHERE employee_id = ?";
        return executeUpdate(query, employeeProfile);
    }

    private boolean executeUpdate(String query, EmployeeProfile employeeProfile) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeProfile.getEmployeeId());
            stmt.setString(2, employeeProfile.getDietPreference());
            stmt.setString(3, employeeProfile.getSpiceLevel());
            stmt.setString(4, employeeProfile.getRegionalPreference());
            stmt.setBoolean(5, employeeProfile.isSweetTooth());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to execute update.", e);
            return false;
        }
    }
}