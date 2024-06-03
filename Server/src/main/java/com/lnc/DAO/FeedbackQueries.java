package com.lnc.DAO;

import com.lnc.employee.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class FeedbackQueries {
    private final Connection connection;

    public FeedbackQueries() throws SQLException {
        JDBCConnection dbInstance = JDBCConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public boolean addFeedback(Feedback feedback) throws Exception {
        boolean isFeedbackAdded = false;

        Menu menu = new Menu();
        int itemID = menu.getItemID(feedback.getMenuItem());

        LocalDate currentDate = LocalDate.now();

        String query = "INSERT INTO feedback (employee_id, item_id, rating, comment, feedback_date) VALUES (?,?,?,?,?)";

        try(PreparedStatement addFeedbackStmt = connection.prepareStatement(query)) {
            addFeedbackStmt.setString(1, feedback.getEmployeeID());
            addFeedbackStmt.setInt(2, itemID);
            addFeedbackStmt.setInt(3, feedback.getRating());
            addFeedbackStmt.setString(4, feedback.getComment());
            addFeedbackStmt.setString(5, currentDate.toString());

            isFeedbackAdded = addFeedbackStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new Exception("\nFailed to add feedback.\n" + ex.getMessage());
        }
        return isFeedbackAdded;
    }
}
