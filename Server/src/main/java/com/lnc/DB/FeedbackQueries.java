package com.lnc.DB;

import com.lnc.Connection.JDBCConnection;
import com.lnc.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackQueries {
    private final Connection connection;
    Menu menu = new Menu();

    public FeedbackQueries() throws SQLException {
        JDBCConnection dbInstance = JDBCConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public boolean addFeedback(Feedback feedback) throws Exception {
        boolean isFeedbackAdded = false;

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

    public List<Map<String, Object>> viewFeedback(String itemName) throws Exception {
        List<Map<String, Object>> feedbackList = new ArrayList<>();

        int itemID = menu.getItemID(itemName);

        String query = "SELECT employee_id, rating, comment, feedback_date FROM feedback WHERE item_id = ? ORDER BY feedback_date DESC LIMIT 20";

        try(PreparedStatement viewFeedbackStmt = connection.prepareStatement(query)) {
            viewFeedbackStmt.setInt(1, itemID);
            ResultSet rs = viewFeedbackStmt.executeQuery();

            while(rs.next()) {
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

        String query = "SELECT AVG(rating) AS weekly_rating, COUNT(*) AS weekly_reviews FROM feedback WHERE item_id = ? AND feedback_date >= CURDATE() - INTERVAL 7 DAY";

        try(PreparedStatement weeklyStatsStmt = connection.prepareStatement(query)){
            weeklyStatsStmt.setInt(1, itemID);
            ResultSet rs = weeklyStatsStmt.executeQuery();
            while(rs.next()){
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

        String query = "SELECT AVG(rating) AS overall_rating, COUNT(*) AS overall_reviews FROM feedback WHERE item_id = ?";

        try(PreparedStatement overallStatsStmt = connection.prepareStatement(query)){
            overallStatsStmt.setInt(1, itemID);
            ResultSet rs = overallStatsStmt.executeQuery();
            while(rs.next()){
                overallStats.put("overall_rating", rs.getDouble("overall_rating"));
                overallStats.put("overall_reviews", rs.getInt("overall_reviews"));
            }
        } catch (SQLException e) {
            throw new Exception("\nError getting overall stats.\n" + e.getMessage());
        }

        return overallStats;
    }
}
