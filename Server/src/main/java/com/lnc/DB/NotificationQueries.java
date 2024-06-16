package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationQueries {
  private final Connection connection;

  public NotificationQueries() throws SQLException {
    JDBCConnection dbInstance = JDBCConnection.getInstance();
    connection = dbInstance.getConnection();
  }

  public boolean insertRolloutNotification() throws Exception {
    boolean isNotificationInserted = false;

    String query = "INSERT INTO notification (message) VALUES ('Menu rolled out for today')";

    try (PreparedStatement insertNotificationStmt = connection.prepareStatement(query)) {
      isNotificationInserted = insertNotificationStmt.executeUpdate() > 0;
    } catch (SQLException ex) {
      throw new Exception("\nFailed to insert notification.\n" + ex.getMessage());
    }

    return isNotificationInserted;
  }

  public List<Notification> getAllUserNotifications(String employeeID) throws Exception {
    List<Notification> notifications = new ArrayList<>();

    String query =
        "SELECT n.message, n.created_at FROM notification n JOIN employee_notifications en ON n.notification_id = en.notification_id WHERE en.employee_id = ? LIMIT 10";

    try (PreparedStatement getNotificationsStmt = connection.prepareStatement(query)) {
      getNotificationsStmt.setString(1, employeeID);
      ResultSet rs = getNotificationsStmt.executeQuery();

      while (rs.next()) {
        String message = rs.getString("message");
        String createdAt = rs.getString("created_at");
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setCreatedAt(createdAt);
        notifications.add(notification);
      }
        boolean isStatusUpdated = updateNotificationStatus(employeeID, "READ");
        if (!isStatusUpdated) {
            System.out.println("Failed to update notification status.");
        }
    } catch (SQLException ex) {
      throw new Exception("\nFailed to get notifications.\n" + ex.getMessage());
    }

    return notifications;
  }

  public List<Notification> getNewNotifications(String employeeID) throws Exception {
    List<Notification> notifications = new ArrayList<>();

    String query =
        "SELECT n.message, n.created_at FROM notification n JOIN employee_notifications en ON n.notification_id = en.notification_id WHERE en.employee_id = ? AND status = 'PENDING' LIMIT 10";

    try (PreparedStatement getNotificationsStmt = connection.prepareStatement(query)) {
      getNotificationsStmt.setString(1, employeeID);
      ResultSet rs = getNotificationsStmt.executeQuery();

      while (rs.next()) {
        String message = rs.getString("message");
        String createdAt = rs.getString("created_at");
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setCreatedAt(createdAt);
        notifications.add(notification);
      }
      boolean isStatusUpdated = updateNotificationStatus(employeeID, "DELIVERED");
      if (!isStatusUpdated) {
        System.out.println("Failed to update notification status.");
      }
    } catch (SQLException ex) {
      throw new Exception("\nFailed to get notifications.\n" + ex.getMessage());
    }

    return notifications;
  }

  private boolean updateNotificationStatus(String employeeID, String status) {
    boolean isStatusUpdated = false;
    String query = "UPDATE employee_notifications SET status = ? WHERE employee_id = ?";

    try (PreparedStatement updateNotificationStatusStmt = connection.prepareStatement(query)) {
      updateNotificationStatusStmt.setString(1, status);
      updateNotificationStatusStmt.setString(2, employeeID);
      isStatusUpdated = updateNotificationStatusStmt.executeUpdate() > 0;
    } catch (SQLException ex) {
      System.out.println("\nFailed to update notification status.\n" + ex.getMessage());
    }
    return isStatusUpdated;
  }
}
