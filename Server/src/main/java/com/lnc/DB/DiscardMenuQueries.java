package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscardMenuQueries {
    private final Logger logger = Logger.getLogger(DiscardMenuQueries.class.getName());

    public boolean addToDiscardMenu(int itemID) {
        String query = "INSERT INTO discard_menu (item_id) VALUES (?)";
        return executeUpdate(query, itemID);
    }

    public List<String> getAllDiscardedItems() {
        List<String> discardedItems = new ArrayList<>();
        String query = "SELECT item_name FROM menu WHERE item_id IN (SELECT item_id FROM discard_menu)";

        try (Connection connection = getConnection();
             PreparedStatement getDiscardedItemsStmt = connection.prepareStatement(query);
             ResultSet resultSet = getDiscardedItemsStmt.executeQuery()) {

            while (resultSet.next()) {
                discardedItems.add(resultSet.getString("item_name"));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to get discarded items.", ex);
        }
        return discardedItems;
    }

    public boolean removeFromDiscardMenu(String itemName) {
        MenuQueries menuQueries = new MenuQueries();
        int itemID = menuQueries.getItemID(itemName);

        String query = "DELETE FROM discard_menu WHERE item_id = ?";
        return executeUpdate(query, itemID);
    }

    private boolean executeUpdate(String query, int itemID) {
        boolean result = false;

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, itemID);
            result = stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to execute update.", ex);
        }
        return result;
    }

    private Connection getConnection() throws SQLException {
        return JDBCConnection.getInstance().getConnection();
    }
}
