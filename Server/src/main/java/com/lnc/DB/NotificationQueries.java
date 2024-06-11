package com.lnc.DB;

import com.lnc.Connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotificationQueries {
    private final Connection connection;

    public NotificationQueries() throws SQLException {
        JDBCConnection dbInstance = JDBCConnection.getInstance();
        connection = dbInstance.getConnection();
    }

    public boolean insertRolloutNotification() {
        boolean isNotificationInserted = false;

        String query = "INSERT INTO notification (message) VALUES ('Menu rolled out for today')";

        try(PreparedStatement insertNotificationStmt = connection.prepareStatement(query)) {
            isNotificationInserted = insertNotificationStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Failed to insert notification: " + ex.getMessage());
        }

        return isNotificationInserted;
    }
}
