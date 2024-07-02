package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.Feedback;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class FeedbackQueries {
  Logger logger = Logger.getLogger(FeedbackQueries.class.getName());
  private Connection connection;
  private final MenuQueries menu = new MenuQueries();

  public FeedbackQueries() {
    try {
      JDBCConnection dbInstance = JDBCConnection.getInstance();
      this.connection = dbInstance.getConnection();
    } catch (SQLException e) {
      logger.severe("Failed to connect to the database.\n" + e.getMessage());
    }
  }

  public boolean addFeedback(Feedback feedback) {
    String query = "INSERT INTO feedback (employee_id, item_id, rating, comment, feedback_date) VALUES (?,?,?,?,?)";
    LocalDate currentDate = LocalDate.now();

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      prepareAddFeedbackStatement(stmt, feedback, currentDate);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.severe("Failed to add feedback.\n" + e.getMessage());
      return false;
    }
  }

  private void prepareAddFeedbackStatement(PreparedStatement stmt, Feedback feedback, LocalDate currentDate) throws SQLException {
    int itemID = menu.getItemID(feedback.getMenuItem());
    stmt.setString(1, feedback.getEmployeeID());
    stmt.setInt(2, itemID);
    stmt.setInt(3, feedback.getRating());
    stmt.setString(4, feedback.getComment());
    stmt.setString(5, currentDate.toString());
  }

  public List<Map<String, Object>> viewFeedback(String itemName) {
    String query = "SELECT employee_id, rating, comment, feedback_date FROM feedback WHERE item_id = ? ORDER BY feedback_date DESC LIMIT 20";
    int itemID = menu.getItemID(itemName);
    List<Map<String, Object>> feedbackList = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, itemID);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          feedbackList.add(createFeedbackMap(rs));
        }
      }
    } catch (SQLException e) {
      logger.severe("Failed to view feedback.\n" + e.getMessage());
    }
    return feedbackList;
  }

  private Map<String, Object> createFeedbackMap(ResultSet rs) throws SQLException {
    Map<String, Object> feedback = new HashMap<>();
    UserDetailsQueries userDetails = new UserDetailsQueries();
    String userName = userDetails.getUserName(rs.getString("employee_id"));
    feedback.put("user_name", userName);
    feedback.put("rating", rs.getInt("rating"));
    feedback.put("comment", rs.getString("comment"));
    feedback.put("feedback_date", rs.getString("feedback_date"));
    return feedback;
  }

  public Map<String, Object> getWeeklyStats(String itemName) {
    String query = "SELECT AVG(rating) AS weekly_rating, COUNT(*) AS weekly_reviews FROM feedback WHERE item_id = ? AND feedback_date >= CURDATE() - INTERVAL 7 DAY";
    return getStats(itemName, query);
  }

  public Map<String, Object> getOverallStats(String itemName) {
    String query = "SELECT AVG(rating) AS overall_rating, COUNT(*) AS overall_reviews FROM feedback WHERE item_id = ?";
    return getStats(itemName, query);
  }

  private Map<String, Object> getStats(String itemName, String query) {
    int itemID = menu.getItemID(itemName);
    Map<String, Object> stats = new HashMap<>();

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, itemID);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          stats.put("average_rating", rs.getDouble("average_rating"));
          stats.put("review_count", rs.getInt("review_count"));
        }
      }
    } catch (SQLException e) {
      logger.severe("Failed to get stats.\n" + e.getMessage());
    }
    return stats;
  }

  public List<Map<String, Object>> generateFeedbackReport(String month, String year) {
    String query = "SELECT m.item_name, u.name AS employee_name, f.rating, f.comment, f.feedback_date, " +
            "(SELECT AVG(f2.rating) FROM feedback f2 WHERE f2.item_id = f.item_id AND " +
            "MONTH(f2.feedback_date) = ? AND YEAR(f2.feedback_date) = ?) AS avg_rating " +
            "FROM feedback f " +
            "JOIN user_details u ON f.employee_id = u.employee_id " +
            "JOIN menu m ON f.item_id = m.item_id " +
            "WHERE MONTH(f.feedback_date) = ? AND YEAR(f.feedback_date) = ? " +
            "ORDER BY m.item_name, f.feedback_date";

    List<Map<String, Object>> reportData = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      prepareReportStatement(stmt, month, year);
      try (ResultSet rs = stmt.executeQuery()) {
        processFeedbackReportResult(rs, reportData);
      }
    } catch (SQLException e) {
      logger.severe("Failed to generate feedback report.\n" + e.getMessage());
    }
    return reportData;
  }

  private void prepareReportStatement(PreparedStatement stmt, String month, String year) throws SQLException {
    stmt.setInt(1, Integer.parseInt(month));
    stmt.setInt(2, Integer.parseInt(year));
    stmt.setInt(3, Integer.parseInt(month));
    stmt.setInt(4, Integer.parseInt(year));
  }

  private void processFeedbackReportResult(ResultSet rs, List<Map<String, Object>> reportData) throws SQLException {
    String currentItem = "";
    double avgRating = 0;
    int ratingCount = 0;
    Map<String, Object> currentItemData = null;
    List<Map<String, Object>> feedbackList = null;

    while (rs.next()) {
      String itemName = rs.getString("item_name");
      String employeeName = rs.getString("employee_name");
      int rating = rs.getInt("rating");
      String comment = rs.getString("comment");
      Date feedbackDate = rs.getDate("feedback_date");

      if (!itemName.equals(currentItem)) {
        if (currentItemData != null) {
          currentItemData.put("average_rating", avgRating / ratingCount);
          reportData.add(currentItemData);
        }
        currentItem = itemName;
        avgRating = 0;
        ratingCount = 0;
        currentItemData = new HashMap<>();
        currentItemData.put("item_name", itemName);
        feedbackList = new ArrayList<>();
        currentItemData.put("feedbacks", feedbackList);
      }

      avgRating += rating;
      ratingCount++;

      Map<String, Object> feedbackData = new HashMap<>();
      feedbackData.put("employee_name", employeeName);
      feedbackData.put("rating", rating);
      feedbackData.put("comment", comment);
      feedbackData.put("date", feedbackDate.getTime());
      feedbackList.add(feedbackData);
    }

    if (currentItemData != null) {
      currentItemData.put("average_rating", avgRating / ratingCount);
      reportData.add(currentItemData);
    }
  }

  public List<String[]> getReviewCommentsOfItem(String itemName) {
    String query = "SELECT comment, rating FROM feedback WHERE item_id = ?";
    int itemID = menu.getItemID(itemName);
    List<String[]> comments = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, itemID);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          comments.add(new String[]{rs.getString("comment"), ratingToLabel(rs.getInt("rating"))});
        }
      }
    } catch (SQLException e) {
      logger.severe("Failed to get review comments.\n" + e.getMessage());
    }
    return comments;
  }

  private String ratingToLabel(int rating) {
    if (rating >= 4) {
      return "positive";
    } else if (rating == 3) {
      return "neutral";
    } else {
      return "negative";
    }
  }

  public double getAverageRating(String itemName) {
    String query = "SELECT AVG(rating) AS average_rating FROM feedback WHERE item_id = ?";
    int itemID = menu.getItemID(itemName);

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, itemID);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getDouble("average_rating");
        }
      }
    } catch (SQLException e) {
      logger.severe("Failed to get average rating.\n" + e.getMessage());
    }
    return 0;
  }

  public List<Map<String, Object>> getFeedbacksFromLastMonth() {
    String query = "SELECT item_id, comment FROM feedback WHERE feedback_date >= ?";
    List<Map<String, Object>> feedbackList = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setDate(1, Date.valueOf(LocalDate.now().minusMonths(1)));
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          Map<String, Object> feedback = new HashMap<>();
          feedback.put("item_id", rs.getInt("item_id"));
          feedback.put("comment", rs.getString("comment"));
          feedbackList.add(feedback);
        }
      }
    } catch (SQLException e) {
      logger.severe("Failed to get feedbacks from last month.\n" + e.getMessage());
    }
    return feedbackList;
  }
}
