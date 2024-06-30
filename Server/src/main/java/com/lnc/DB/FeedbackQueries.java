package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.Feedback;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FeedbackQueries {
  Logger logger = Logger.getLogger(FeedbackQueries.class.getName());
  private Connection connection;
  private final MenuQueries menu = new MenuQueries();

  public FeedbackQueries() {
    try{
      JDBCConnection dbInstance = JDBCConnection.getInstance();
      this.connection = dbInstance.getConnection();
    } catch (SQLException e) {
      logger.severe("Failed to connect to the database.\n" + e.getMessage());
    }
  }

  public boolean addFeedback(Feedback feedback) {
    boolean isFeedbackAdded = false;

    int itemID = menu.getItemID(feedback.getMenuItem());

    LocalDate currentDate = LocalDate.now();

    String query =
        "INSERT INTO feedback (employee_id, item_id, rating, comment, feedback_date) VALUES (?,?,?,?,?)";

    try (PreparedStatement addFeedbackStmt = connection.prepareStatement(query)) {
      addFeedbackStmt.setString(1, feedback.getEmployeeID());
      addFeedbackStmt.setInt(2, itemID);
      addFeedbackStmt.setInt(3, feedback.getRating());
      addFeedbackStmt.setString(4, feedback.getComment());
      addFeedbackStmt.setString(5, currentDate.toString());

      isFeedbackAdded = addFeedbackStmt.executeUpdate() > 0;
    } catch (SQLException ex) {
      logger.severe("Failed to add feedback.\n" + ex.getMessage());
    }
    return isFeedbackAdded;
  }

  public List<Map<String, Object>> viewFeedback(String itemName) {
    List<Map<String, Object>> feedbackList = new ArrayList<>();

    int itemID = menu.getItemID(itemName);

    String query =
        "SELECT employee_id, rating, comment, feedback_date FROM feedback WHERE item_id = ? ORDER BY feedback_date DESC LIMIT 20";

    try (PreparedStatement viewFeedbackStmt = connection.prepareStatement(query)) {
      viewFeedbackStmt.setInt(1, itemID);
      ResultSet rs = viewFeedbackStmt.executeQuery();

      while (rs.next()) {
        Map<String, Object> feedbacks = new HashMap<>();

        UserDetailsQueries userDetails = new UserDetailsQueries();
        String user_name = userDetails.getUserName(rs.getString("employee_id"));

        feedbacks.put("user_name", user_name);
        feedbacks.put("rating", rs.getInt("rating"));
        feedbacks.put("comment", rs.getString("comment"));
        feedbacks.put("feedback_date", rs.getString("feedback_date"));
        feedbackList.add(feedbacks);
      }
    } catch (SQLException e) {
      logger.severe("Failed to view feedback.\n" + e.getMessage());
    }

    return feedbackList;
  }

  public Map<String, Object> getWeeklyStats(String itemName) {
    Map<String, Object> weeklyStats = new HashMap<>();

    int itemID = menu.getItemID(itemName);

    String query =
        "SELECT AVG(rating) AS weekly_rating, COUNT(*) AS weekly_reviews FROM feedback WHERE item_id = ? AND feedback_date >= CURDATE() - INTERVAL 7 DAY";

    try (PreparedStatement weeklyStatsStmt = connection.prepareStatement(query)) {
      weeklyStatsStmt.setInt(1, itemID);
      ResultSet rs = weeklyStatsStmt.executeQuery();
      while (rs.next()) {
        weeklyStats.put("weekly_rating", rs.getDouble("weekly_rating"));
        weeklyStats.put("weekly_reviews", rs.getInt("weekly_reviews"));
      }
    } catch (SQLException e) {
      logger.severe("Failed to get weekly stats.\n" + e.getMessage());
    }

    return weeklyStats;
  }

  public Map<String, Object> getOverallStats(String itemName) {
    Map<String, Object> overallStats = new HashMap<>();

    int itemID = menu.getItemID(itemName);

    String query =
        "SELECT AVG(rating) AS overall_rating, COUNT(*) AS overall_reviews FROM feedback WHERE item_id = ?";

    try (PreparedStatement overallStatsStmt = connection.prepareStatement(query)) {
      overallStatsStmt.setInt(1, itemID);
      ResultSet rs = overallStatsStmt.executeQuery();
      while (rs.next()) {
        overallStats.put("overall_rating", rs.getDouble("overall_rating"));
        overallStats.put("overall_reviews", rs.getInt("overall_reviews"));
      }
    } catch (SQLException e) {
      logger.severe("Failed to get overall stats.\n" + e.getMessage());
    }

    return overallStats;
  }

  public List<Map<String, Object>> generateFeedbackReport(String month, String year) {

    String query =
        "SELECT m.item_name, u.name AS employee_name, f.rating, f.comment, f.feedback_date, "
            + "(SELECT AVG(f2.rating) FROM feedback f2 WHERE f2.item_id = f.item_id AND "
            + "MONTH(f2.feedback_date) = ? AND YEAR(f2.feedback_date) = ?) AS avg_rating "
            + "FROM feedback f "
            + "JOIN user_details u ON f.employee_id = u.employee_id "
            + "JOIN menu m ON f.item_id = m.item_id "
            + "WHERE MONTH(f.feedback_date) = ? AND YEAR(f.feedback_date) = ? "
            + "ORDER BY m.item_name, f.feedback_date";

    List<Map<String, Object>> reportData = new ArrayList<>();

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      preparedStatement.setInt(1, Integer.parseInt(month));
      preparedStatement.setInt(2, Integer.parseInt(year));
      preparedStatement.setInt(3, Integer.parseInt(month));
      preparedStatement.setInt(4, Integer.parseInt(year));

      ResultSet resultSet = preparedStatement.executeQuery();

      String currentItem = "";
      double avgRating = 0;
      int ratingCount = 0;
      Map<String, Object> currentItemData = null;
      List<Map<String, Object>> feedbackList = null;

      while (resultSet.next()) {
        String itemName = resultSet.getString("item_name");
        String employeeName = resultSet.getString("employee_name");
        int rating = resultSet.getInt("rating");
        String comment = resultSet.getString("comment");
        Date feedbackDate = resultSet.getDate("feedback_date");

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

    } catch (SQLException e) {
      logger.severe("Failed to generate feedback report.\n" + e.getMessage());
    }

    return reportData;
  }

  public List<String[]> getReviewCommentsOfItem(String itemName) {
    List<String[]> comments = new ArrayList<>();
    int itemID = menu.getItemID(itemName);
    String query = "SELECT comment, rating FROM feedback WHERE item_id = ?";
    try (PreparedStatement getCommentsStmt = connection.prepareStatement(query)) {
      getCommentsStmt.setInt(1, itemID);
      ResultSet rs = getCommentsStmt.executeQuery();
      while (rs.next()) {
        comments.add(new String[] {rs.getString("comment"), ratingToLabel(rs.getInt("rating"))});
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
    int itemID = menu.getItemID(itemName);
    String query = "SELECT AVG(rating) AS average_rating FROM feedback WHERE item_id = ?";
    try (PreparedStatement getAverageRatingStmt = connection.prepareStatement(query)) {
      getAverageRatingStmt.setInt(1, itemID);
      ResultSet rs = getAverageRatingStmt.executeQuery();
      if (rs.next()) {
        return rs.getDouble("average_rating");
      }
    } catch (SQLException e) {
      logger.severe("Failed to get average rating.\n" + e.getMessage());
    }
    return 0;
  }

  public List<Map<String, Object>> getFeedbacksFromLastMonth() {
    String query = "SELECT item_id, comment FROM feedback WHERE feedback_date >= ?";
    List<Map<String, Object>> feedbackList = new ArrayList<>();

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setDate(1, Date.valueOf(LocalDate.now().minusMonths(1)));
      try (ResultSet rs = pstmt.executeQuery()) {
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
