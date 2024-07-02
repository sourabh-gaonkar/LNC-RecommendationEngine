package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class EmployeeOrderQueries {
    private final Logger logger = Logger.getLogger(EmployeeOrderQueries.class.getName());
    private Connection connection;
    private final MenuQueries menuQueries = new MenuQueries();

    public EmployeeOrderQueries() {
        try {
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            connection = dbInstance.getConnection();
        } catch (SQLException ex) {
            logger.severe("Failed to connect to database: " + ex.getMessage());
        }
    }

    private int getItemId(String menuItem) {
        return menuQueries.getItemID(menuItem);
    }

    public int getFeedbacksLeft(Feedback feedback) {
        String query = "SELECT available_feedbacks FROM employee_orders WHERE employee_id = ? AND item_id = ?";
        int feedbacksLeft = 0;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, feedback.getEmployeeID());
            stmt.setInt(2, getItemId(feedback.getMenuItem()));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    feedbacksLeft = rs.getInt("available_feedbacks");
                }
            }
        } catch (SQLException e) {
            logger.severe("Error while getting available feedbacks: " + e.getMessage());
        }

        return feedbacksLeft;
    }

    public boolean isRowPresent(Feedback feedback) {
        return isRowPresent(feedback.getEmployeeID(), feedback.getMenuItem());
    }

    public boolean isRowPresent(String employeeId, String itemName) {
        String query = "SELECT 1 FROM employee_orders WHERE employee_id = ? AND item_id = ?";
        boolean isRowPresent = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.setInt(2, getItemId(itemName));

            try (ResultSet rs = stmt.executeQuery()) {
                isRowPresent = rs.next();
            }
        } catch (SQLException e) {
            logger.severe("Error while checking if row is present: " + e.getMessage());
        }

        return isRowPresent;
    }

    public boolean addFeedbackCount(String employeeId, String itemName) {
        String query = "UPDATE employee_orders SET available_feedbacks = available_feedbacks + 1 WHERE employee_id = ? AND item_id = ?";
        return updateFeedbackCount(employeeId, itemName, query);
    }

    public boolean subtractFeedbackCount(Feedback feedback) {
        String query = "UPDATE employee_orders SET available_feedbacks = available_feedbacks - 1 WHERE employee_id = ? AND item_id = ?";
        return updateFeedbackCount(feedback.getEmployeeID(), feedback.getMenuItem(), query);
    }

    private boolean updateFeedbackCount(String employeeId, String itemName, String query) {
        boolean isUpdated = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.setInt(2, getItemId(itemName));

            isUpdated = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error while updating feedback count: " + e.getMessage());
        }

        return isUpdated;
    }

    public boolean addNewItemFeedbackValue(String employeeID, String menuItem) {
        String query = "INSERT INTO employee_orders (employee_id, item_id, available_feedbacks) VALUES (?, ?, 1)";
        boolean isAdded = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeID);
            stmt.setInt(2, getItemId(menuItem));

            isAdded = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error while adding new item feedback value: " + e.getMessage());
        }

        return isAdded;
    }
}
