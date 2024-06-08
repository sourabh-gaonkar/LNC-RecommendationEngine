package com.lnc.DB;

import com.lnc.Connection.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationEngineQueries {
    private final Connection connection;

    public RecommendationEngineQueries() throws SQLException {
        JDBCConnection dbInstance = JDBCConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public Map<String, List<Map<String, Object>>> getAllData() throws SQLException {
        Map<String, String> queries = getQueries();
        Map<String, List<Map<String, Object>>> dataFrames = new HashMap<>();

        for (Map.Entry<String, String> entry : queries.entrySet()) {
            dataFrames.put(entry.getKey(), getData(entry.getValue()));
        }

        return dataFrames;
    }

    public List<Map<String, Object>> getData(String query) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                result.add(row);
            }
        }
        return result;
    }

    private Map<String, String> getQueries() {
        Map<String, String> queries = new HashMap<>();
        queries.put("weekly_ratings", """
            SELECT item_id, AVG(rating) as avg_weekly_rating
            FROM feedback
            WHERE feedback_date >= CURDATE() - INTERVAL 7 DAY
            GROUP BY item_id;
        """);
        queries.put("monthly_ratings", """
            SELECT item_id, AVG(rating) as avg_monthly_rating
            FROM feedback
            WHERE feedback_date >= CURDATE() - INTERVAL 1 MONTH
            GROUP BY item_id;
        """);
        queries.put("overall_ratings", """
            SELECT item_id, AVG(rating) as avg_overall_rating
            FROM feedback
            GROUP BY item_id;
        """);
        queries.put("votes", """
            SELECT item_id, SUM(votes) as total_votes
            FROM menu_rollout
            GROUP BY item_id;
        """);
        queries.put("prices", """
            SELECT item_id, item_name, price, category
            FROM menu;
        """);
        queries.put("day_of_week_ratings", """
            SELECT item_id, DAYOFWEEK(feedback_date) as day_of_week, AVG(rating) as avg_rating
            FROM feedback
            GROUP BY item_id, DAYOFWEEK(feedback_date);
        """);
        queries.put("last_rollout", """
            SELECT item_id, MAX(date) as last_rollout_date
            FROM menu_rollout
            GROUP BY item_id;
        """);
        queries.put("day_of_week_votes", """
            SELECT item_id, DAYOFWEEK(date) as day_of_week, SUM(votes) as total_day_votes
            FROM menu_rollout
            GROUP BY item_id, DAYOFWEEK(date);
        """);
        return queries;
    }
}
