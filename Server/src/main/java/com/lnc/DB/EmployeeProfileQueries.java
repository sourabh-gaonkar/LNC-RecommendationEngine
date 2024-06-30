package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.EmployeeProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EmployeeProfileQueries {
    private final Logger logger = Logger.getLogger(DiscardMenuQueries.class.getName());
    private Connection connection;

    public EmployeeProfileQueries() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.severe("Failed to connect to the database.\n" + e.getMessage());
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
            logger.severe("Failed to get employee preferences.\n" + e.getMessage());
        }

        return preferences;
    }

    public boolean addEmployeePreferences(EmployeeProfile employeeProfile) {
        String query = "INSERT INTO employee_profile (employee_id, diet_preference, spice_level, regional_preference, sweet_tooth) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement addPreferenceStmt = connection.prepareStatement(query)) {
            addPreferenceStmt.setString(1, employeeProfile.getEmployeeId());
            addPreferenceStmt.setString(2, employeeProfile.getDietPreference());
            addPreferenceStmt.setString(3, employeeProfile.getSpiceLevel());
            addPreferenceStmt.setString(4, employeeProfile.getRegionalPreference());
            addPreferenceStmt.setBoolean(5, employeeProfile.isSweetTooth());

            int rowsAffected = addPreferenceStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.severe("Failed to add employee preferences.\n" + e.getMessage());
            return false;
        }
    }

    public boolean updateEmployeePreferences(EmployeeProfile employeeProfile) {
        String query = "UPDATE employee_profile SET diet_preference = ?, spice_level = ?, regional_preference = ?, sweet_tooth = ? " +
                "WHERE employee_id = ?";

        try (PreparedStatement updatePreferenceStmt = connection.prepareStatement(query)) {
            updatePreferenceStmt.setString(1, employeeProfile.getDietPreference());
            updatePreferenceStmt.setString(2, employeeProfile.getSpiceLevel());
            updatePreferenceStmt.setString(3, employeeProfile.getRegionalPreference());
            updatePreferenceStmt.setBoolean(4, employeeProfile.isSweetTooth());
            updatePreferenceStmt.setString(5, employeeProfile.getEmployeeId());

            int rowsAffected = updatePreferenceStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.severe("Failed to update employee preferences.\n" + e.getMessage());
            return false;
        }
    }
}
