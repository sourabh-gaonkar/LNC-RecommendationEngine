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
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            connection = dbInstance.getConnection();
        } catch (SQLException ex) {
            logger.severe("Failed to connect to database: " + ex.getMessage());
        }
    }

    public int getFeedbacksLeft(Feedback feedback) {
        int feedbacksLeft = 0;

        String query = "SELECT available_feedbacks FROM employee_orders WHERE employee_id = ? AND item_id = ?";

        int itemId = menuQueries.getItemID(feedback.getMenuItem());

        try(PreparedStatement getAvailableFeedbackStmt = connection.prepareStatement(query)) {
            getAvailableFeedbackStmt.setString(1, feedback.getEmployeeID());
            getAvailableFeedbackStmt.setInt(2, itemId);

            ResultSet rs = getAvailableFeedbackStmt.executeQuery();
            if (rs.next()) {
                feedbacksLeft = rs.getInt("available_feedbacks");
            }
        } catch (SQLException e) {
            logger.severe("Error while getting available feedbacks: " + e.getMessage());
        }

        return feedbacksLeft;
    }

    public boolean isRowPresent(Feedback feedback) {
        boolean isRowPresent = false;

        String query = "SELECT * FROM employee_orders WHERE employee_id = ? AND item_id = ?";

        int itemId = menuQueries.getItemID(feedback.getMenuItem());

        try(PreparedStatement checkRowStmt = connection.prepareStatement(query)) {
            checkRowStmt.setString(1, feedback.getEmployeeID());
            checkRowStmt.setInt(2, itemId);

            isRowPresent = checkRowStmt.executeQuery().next();
        } catch (SQLException e) {
            logger.severe("Error while checking if row is present: " + e.getMessage());
        }

        return isRowPresent;
    }

    public boolean isRowPresent(String employeeId, String itemName) {
        boolean isRowPresent = false;

        String query = "SELECT * FROM employee_orders WHERE employee_id = ? AND item_id = ?";

        int itemId = menuQueries.getItemID(itemName);

        try(PreparedStatement checkRowStmt = connection.prepareStatement(query)) {
            checkRowStmt.setString(1, employeeId);
            checkRowStmt.setInt(2, itemId);

            isRowPresent = checkRowStmt.executeQuery().next();
        } catch (SQLException e) {
            logger.severe("Error while checking if row is present: " + e.getMessage());
        }

        return isRowPresent;
    }

    public boolean addFeedbackCount(String employeeId, String itemName) {
        boolean isAdded = false;

        String query = "UPDATE employee_orders SET available_feedbacks = available_feedbacks + 1 WHERE employee_id = ? AND item_id = ?";

        int itemId = menuQueries.getItemID(itemName);

        try(PreparedStatement addFeedbackCountStmt = connection.prepareStatement(query)) {
            addFeedbackCountStmt.setString(1, employeeId);
            addFeedbackCountStmt.setInt(2, itemId);

            isAdded = addFeedbackCountStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error while adding feedback count: " + e.getMessage());
        }

        return isAdded;
    }

    public boolean subtractFeedbackCount(Feedback feedback) {
        boolean isSubtracted = false;

        String query = "UPDATE employee_orders SET available_feedbacks = available_feedbacks - 1 WHERE employee_id = ? AND item_id = ?";

        int itemId = menuQueries.getItemID(feedback.getMenuItem());

        try(PreparedStatement subtractFeedbackCountStmt = connection.prepareStatement(query)) {
            subtractFeedbackCountStmt.setString(1, feedback.getEmployeeID());
            subtractFeedbackCountStmt.setInt(2, itemId);

            isSubtracted = subtractFeedbackCountStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error while subtracting feedback count: " + e.getMessage());
        }

        return isSubtracted;
    }

    public boolean addNewItemFeedbackValue(String employeeID, String menuItem) {
        boolean isAdded = false;

        String query = "INSERT INTO employee_orders (employee_id, item_id, available_feedbacks) VALUES (?, ?, 1)";

        int itemId = menuQueries.getItemID(menuItem);

        try(PreparedStatement addNewItemFeedbackValueStmt = connection.prepareStatement(query)) {
            addNewItemFeedbackValueStmt.setString(1, employeeID);
            addNewItemFeedbackValueStmt.setInt(2, itemId);

            isAdded = addNewItemFeedbackValueStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error while adding new item feedback value: " + e.getMessage());
        }

        return isAdded;
    }
}
