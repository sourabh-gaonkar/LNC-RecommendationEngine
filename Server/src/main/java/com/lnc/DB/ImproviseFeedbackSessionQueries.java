package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ImproviseFeedbackSessionQueries {
    private final Logger logger = Logger.getLogger(DiscardMenuQueries.class.getName());
    private Connection connection;

    public ImproviseFeedbackSessionQueries() {
        try{
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
        String query = "INSERT INTO improvise_feedback_session (item_id) VALUES (?)";
        try (PreparedStatement createFeedbackSessionStmt = connection.prepareStatement(query)) {
            createFeedbackSessionStmt.setInt(1, itemID);
            boolean isFeedbackSessionCreated = createFeedbackSessionStmt.executeUpdate() > 0;
            if (isFeedbackSessionCreated) feedbackSessionID = getFeedbackSessionID(itemID);
        } catch (SQLException ex) {
            logger.severe("Failed to create feedback session.\n" + ex.getMessage());
        }
        return feedbackSessionID;
    }

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
