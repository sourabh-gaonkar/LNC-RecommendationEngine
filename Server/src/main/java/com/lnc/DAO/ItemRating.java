package com.lnc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ItemRating {
    private final Connection connection;
    Menu menu = new Menu();

    public ItemRating() throws SQLException {
        JDBCConnection dbInstance = JDBCConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public Map<String, Object> getWeeklyStats(String itemName) throws Exception {
        Map<String, Object> weeklyStats = new HashMap<>();

        int itemID = menu.getItemID(itemName);

        String query = "SELECT weekly_rating, weekly_reviews FROM item_rating WHERE item_id = ?";

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

        String query = "SELECT overall_rating, overall_reviews FROM item_rating WHERE item_id = ?";

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
