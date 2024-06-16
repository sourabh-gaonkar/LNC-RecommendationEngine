package com.lnc.DB;

import com.lnc.Connection.JDBCConnection;
import com.lnc.model.Feedback;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackQueries {
  private final Connection connection;
  private final Menu menu = new Menu();

  public FeedbackQueries() throws SQLException {
    JDBCConnection dbInstance = JDBCConnection.getInstance();
    this.connection = dbInstance.getConnection();
  }

  public boolean addFeedback(Feedback feedback) throws Exception {
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
      throw new Exception("\nFailed to add feedback.\n" + ex.getMessage());
    }
    return isFeedbackAdded;
  }

  public List<Map<String, Object>> viewFeedback(String itemName) throws Exception {
    List<Map<String, Object>> feedbackList = new ArrayList<>();

    int itemID = menu.getItemID(itemName);

    String query =
        "SELECT employee_id, rating, comment, feedback_date FROM feedback WHERE item_id = ? ORDER BY feedback_date DESC LIMIT 20";

    try (PreparedStatement viewFeedbackStmt = connection.prepareStatement(query)) {
      viewFeedbackStmt.setInt(1, itemID);
      ResultSet rs = viewFeedbackStmt.executeQuery();

      while (rs.next()) {
        Map<String, Object> feedbacks = new HashMap<>();

        UserDetails userDetails = new UserDetails();
        String user_name = userDetails.getUserName(rs.getString("employee_id"));

        feedbacks.put("user_name", user_name);
        feedbacks.put("rating", rs.getInt("rating"));
        feedbacks.put("comment", rs.getString("comment"));
        feedbacks.put("feedback_date", rs.getString("feedback_date"));
        feedbackList.add(feedbacks);
      }
    } catch (SQLException e) {
      throw new Exception("\nFailed to view feedback.\n" + e.getMessage());
    }

    return feedbackList;
  }

  public Map<String, Object> getWeeklyStats(String itemName) throws Exception {
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
      throw new Exception("\nError getting weekly stats.\n" + e.getMessage());
    }

    return weeklyStats;
  }

  public Map<String, Object> getOverallStats(String itemName) throws Exception {
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
      throw new Exception("\nError getting overall stats.\n" + e.getMessage());
    }

    return overallStats;
  }

  public List<Map<String, Object>> generateFeedbackReport(String month, String year)
      throws Exception {

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
      throw new Exception("\nError generating feedback report.\n" + e.getMessage());
    }

    return reportData;
  }

  public List<String> getReviewCommentsOfItem(String itemName) throws Exception {
    List<String> comments = new ArrayList<>();
    int itemID = menu.getItemID(itemName);
    String query = "SELECT comment FROM feedback WHERE item_id = ? LIMIT 100";
    try (PreparedStatement getCommentsStmt = connection.prepareStatement(query)) {
      getCommentsStmt.setInt(1, itemID);
      ResultSet rs = getCommentsStmt.executeQuery();
      while (rs.next()) {
        comments.add(rs.getString("comment"));
      }
    } catch (SQLException e) {
      throw new Exception("\nError getting review comments.\n" + e.getMessage());
    }
    return comments;
  }
}
