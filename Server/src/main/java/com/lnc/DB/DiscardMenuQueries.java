package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DiscardMenuQueries {
    private final Logger logger = Logger.getLogger(DiscardMenuQueries.class.getName());
    private Connection connection;

    public DiscardMenuQueries() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.severe("Failed to connect to the database.\n" + e.getMessage());
        }
    }

    public boolean addToDiscardMenu(int itemID) {
        boolean isItemAdded = false;

        String query = "INSERT INTO discard_menu (item_id) VALUES (?)";

        try (var addDiscardItemStmt = connection.prepareStatement(query)) {
            addDiscardItemStmt.setInt(1, itemID);

            isItemAdded = addDiscardItemStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.severe("Failed to add item to discard menu.\n" + ex.getMessage());
        }
        return isItemAdded;
    }

    public List<String> getAllDiscardedItems() {
        List<String> discardedItems = new ArrayList<>();

        String query = "SELECT item_name FROM menu WHERE item_id IN (SELECT item_id FROM discard_menu)";

        try (var getDiscardedItemsStmt = connection.prepareStatement(query)) {
            var resultSet = getDiscardedItemsStmt.executeQuery();
            while (resultSet.next()) {
                discardedItems.add(resultSet.getString("item_name"));
            }
        } catch (SQLException ex) {
            logger.severe("Failed to get discarded items.\n" + ex.getMessage());
        }
        return discardedItems;
    }
}
