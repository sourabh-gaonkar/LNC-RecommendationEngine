package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.DailyMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MenuRolloutQueries {
    Logger logger = Logger.getLogger(MenuRolloutQueries.class.getName());
    private Connection connection;
    private final MenuQueries menuQueries = new MenuQueries();
    private final FeedbackQueries feedbackQueries = new FeedbackQueries();

    public MenuRolloutQueries() {
        try {
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            connection = dbInstance.getConnection();
        } catch (SQLException ex) {
            logger.severe("Failed to connect to database.\n" + ex.getMessage());
        }
    }

    public boolean rolloutMenu(DailyMenu dailyMenu) {
        boolean isRolledOut = false;
        LocalDate currentDate = LocalDate.now();

        try {
            // Use try-with-resources for PreparedStatement
            String query = "INSERT INTO menu_rollout (item_id, date, votes) VALUES (?, ?, 0)";
            try (PreparedStatement rolloutMenuStmt = connection.prepareStatement(query)) {
                // Roll out breakfast items
                isRolledOut = executeRollout(dailyMenu.getBreakfastItems(), rolloutMenuStmt, currentDate);

                // Roll out lunch items
                isRolledOut = isRolledOut && executeRollout(dailyMenu.getLunchItems(), rolloutMenuStmt, currentDate);

                // Roll out snack items
                isRolledOut = isRolledOut && executeRollout(dailyMenu.getSnackItems(), rolloutMenuStmt, currentDate);

                // Roll out dinner items
                isRolledOut = isRolledOut && executeRollout(dailyMenu.getDinnerItems(), rolloutMenuStmt, currentDate);
            }
        } catch (SQLException ex) {
            logger.severe("Failed to roll out menu.\n" + ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return isRolledOut;
    }

    private boolean executeRollout(List<String> items, PreparedStatement stmt, LocalDate currentDate) throws SQLException {
        boolean isAdded = false;
        for (String item : items) {
            int itemID = menuQueries.getItemID(item);
            stmt.setInt(1, itemID);
            stmt.setString(2, currentDate.toString());
            isAdded = stmt.executeUpdate() > 0;
        }
        return isAdded;
    }

    public List<Map<String, Object>> getTodaysMenu() {
        List<Map<String, Object>> todaysMenu = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);

        String query = """
                WITH RankedItems AS (
                    SELECT m.item_name, m.price, m.category,
                    ROW_NUMBER() OVER (PARTITION BY m.category ORDER BY mr.votes DESC, m.item_name) AS rn
                    FROM menu_rollout mr
                    JOIN menu m ON mr.item_id = m.item_id
                    WHERE mr.date = ?
                )
                SELECT item_name, price, category
                FROM RankedItems
                WHERE rn <= 3;
                """;

        try (PreparedStatement getTodaysMenuStmt = connection.prepareStatement(query)) {
            getTodaysMenuStmt.setString(1, yesterday.toString());
            ResultSet rs = getTodaysMenuStmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> item = Map.of(
                        "item_name", rs.getString("item_name"),
                        "price", rs.getDouble("price"),
                        "category", rs.getString("category"),
                        "average_rating", feedbackQueries.getAverageRating(rs.getString("item_name"))
                );
                todaysMenu.add(item);
            }
        } catch (SQLException ex) {
            logger.severe("Failed to get today's menu.\n" + ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return todaysMenu;
    }

    public List<Map<String, Object>> getTomorrowsMenu() {
        List<Map<String, Object>> tomorrowsMenu = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        String query = "SELECT m.item_name, m.price, m.category FROM menu_rollout mr " +
                "JOIN menu m ON mr.item_id = m.item_id WHERE mr.date = ?";

        try (PreparedStatement getTomorrowsMenuStmt = connection.prepareStatement(query)) {
            getTomorrowsMenuStmt.setString(1, currentDate.toString());
            ResultSet rs = getTomorrowsMenuStmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> item = Map.of(
                        "item_name", rs.getString("item_name"),
                        "price", rs.getDouble("price"),
                        "category", rs.getString("category"),
                        "average_rating", feedbackQueries.getAverageRating(rs.getString("item_name"))
                );
                tomorrowsMenu.add(item);
            }
        } catch (SQLException ex) {
            logger.severe("Failed to get tomorrow's menu.\n" + ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tomorrowsMenu;
    }

    public boolean voteForItem(String itemName) {
        boolean isVoted = false;
        LocalDate currentDate = LocalDate.now();

        String query = "UPDATE menu_rollout SET votes = votes + 1 WHERE item_id = ? AND date = ?";

        try (PreparedStatement voteForItemStmt = connection.prepareStatement(query)) {
            int itemID = menuQueries.getItemID(itemName);
            voteForItemStmt.setInt(1, itemID);
            voteForItemStmt.setString(2, currentDate.toString());
            isVoted = voteForItemStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.severe("Failed to vote for item.\n" + ex.getMessage());
        }

        return isVoted;
    }
}
