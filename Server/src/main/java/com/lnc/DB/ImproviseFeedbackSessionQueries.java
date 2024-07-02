package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ImproviseFeedbackSessionQueries {
    private final Logger logger = Logger.getLogger(ImproviseFeedbackSessionQueries.class.getName());
    private Connection connection;

    public ImproviseFeedbackSessionQueries() {
        try {
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.severe("Failed to connect to the database.\n" + e.getMessage());
        }
    }

    public int createFeedbackSession(String itemName) {
        int feedbackSessionID = 0;
        MenuQueries menuQueries = new MenuQueries();
        int itemID = menuQueries.getItemID(itemName);
        String createQuery = "INSERT INTO improvise_feedback_session (item_id) VALUES (?)";
        String selectQuery = "SELECT session_id FROM improvise_feedback_session WHERE item_id = ?";

        try (PreparedStatement createStmt = connection.prepareStatement(createQuery);
             PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {

            // Create feedback session
            createStmt.setInt(1, itemID);
            boolean isFeedbackSessionCreated = createStmt.executeUpdate() > 0;

            if (isFeedbackSessionCreated) {
                // Retrieve feedback session ID
                selectStmt.setInt(1, itemID);
                ResultSet resultSet = selectStmt.executeQuery();
                if (resultSet.next()) {
                    feedbackSessionID = resultSet.getInt("session_id");
                }
            }

        } catch (SQLException ex) {
            logger.severe("Failed to create or retrieve feedback session.\n" + ex.getMessage());
        }
        return feedbackSessionID;
    }

    // No change needed in getFeedbackSessionID method as per your request
    public int getFeedbackSessionID(int itemID) {
        int feedbackSessionID = 0;
        String query = "SELECT session_id FROM improvise_feedback_session WHERE item_id = ?";
        try (PreparedStatement getFeedbackSessionIDStmt = connection.prepareStatement(query)) {
            getFeedbackSessionIDStmt.setInt(1, itemID);
            ResultSet resultSet = getFeedbackSessionIDStmt.executeQuery();
            if (resultSet.next()) {
                feedbackSessionID = resultSet.getInt("session_id");
            }
        } catch (SQLException ex) {
            logger.severe("Failed to get feedback session ID.\n" + ex.getMessage());
        }
        return feedbackSessionID;
    }
}
