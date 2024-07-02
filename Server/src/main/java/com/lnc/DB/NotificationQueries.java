package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NotificationQueries {
  private static final Logger logger = Logger.getLogger(NotificationQueries.class.getName());
  private final Connection connection;

  public NotificationQueries() {
    this.connection = getConnection();
  }

  private Connection getConnection() {
    try {
      return JDBCConnection.getInstance().getConnection();
    } catch (SQLException ex) {
      logger.severe("Failed to connect to database.\n" + ex.getMessage());
      throw new RuntimeException("Failed to connect to database", ex);
    }
  }

  public boolean insertRolloutNotification() {
    String query = "INSERT INTO notification (message) VALUES (?)";
    try (PreparedStatement insertNotificationStmt = connection.prepareStatement(query)) {
      insertNotificationStmt.setString(1, "Menu rolled out for today");
      return insertNotificationStmt.executeUpdate() > 0;
    } catch (SQLException ex) {
      logger.severe("Failed to insert notification.\n" + ex.getMessage());
      return false;
    }
  }

  public List<Notification> getAllUserNotifications(String employeeID) {
    return getUserNotifications(employeeID, "READ");
  }

  public List<Notification> getNewNotifications(String employeeID) {
    return getUserNotifications(employeeID, "DELIVERED");
  }

  private List<Notification> getUserNotifications(String employeeID, String status) {
    List<Notification> notifications = new ArrayList<>();
    String query = "SELECT n.message, n.created_at " +
            "FROM notification n " +
            "JOIN employee_notifications en ON n.notification_id = en.notification_id " +
            "WHERE en.employee_id = ? AND status = ? LIMIT 10";

    try (PreparedStatement getNotificationsStmt = connection.prepareStatement(query)) {
      getNotificationsStmt.setString(1, employeeID);
      getNotificationsStmt.setString(2, status);
      ResultSet rs = getNotificationsStmt.executeQuery();

      while (rs.next()) {
        Notification notification = new Notification();
        notification.setMessage(rs.getString("message"));
        notification.setCreatedAt(rs.getString("created_at"));
        notifications.add(notification);
      }

      updateNotificationStatus(employeeID, status);
    } catch (SQLException ex) {
      logger.severe("Failed to get notifications.\n" + ex.getMessage());
    }

    return notifications;
  }

  private void updateNotificationStatus(String employeeID, String status) {
    String query = "UPDATE employee_notifications SET status = ? WHERE employee_id = ?";
    try (PreparedStatement updateNotificationStatusStmt = connection.prepareStatement(query)) {
      updateNotificationStatusStmt.setString(1, status);
      updateNotificationStatusStmt.setString(2, employeeID);
      updateNotificationStatusStmt.executeUpdate();
    } catch (SQLException ex) {
      logger.severe("Failed to update notification status.\n" + ex.getMessage());
    }
  }
}
