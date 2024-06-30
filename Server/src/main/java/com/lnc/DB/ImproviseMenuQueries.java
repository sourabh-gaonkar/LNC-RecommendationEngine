package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ImproviseMenuQueries {
    private final Logger logger = Logger.getLogger(DiscardMenuQueries.class.getName());
    private Connection connection;

    public ImproviseMenuQueries() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.severe("Failed to connect to the database.\n" + e.getMessage());
        }
    }

    public boolean addToImproviseMenu(String itemName) {
        boolean isItemAdded = false;

        MenuQueries menuQueries = new MenuQueries();
        int itemID = menuQueries.getItemID(itemName);

        String query = "INSERT INTO improvise_menu (item_id) VALUES (?)";

        try (PreparedStatement addImproviseItemStmt = connection.prepareStatement(query)) {
            addImproviseItemStmt.setInt(1, itemID);

            isItemAdded = addImproviseItemStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.severe("Failed to add item to improvise menu.\n" + ex.getMessage());
        }
        return isItemAdded;
    }

    public List<String> getAllImproviseList() {
        List<String> improviseItems = new ArrayList<>();

        String query = "SELECT item_name FROM menu WHERE item_id IN (SELECT item_id FROM improvise_menu)";

        try (var getImproviseItemStmt = connection.prepareStatement(query)) {
            var resultSet = getImproviseItemStmt.executeQuery();
            while (resultSet.next()) {
                improviseItems.add(resultSet.getString("item_name"));
            }
        } catch (SQLException ex) {
            logger.severe("Failed to get improvise items.\n" + ex.getMessage());
        }
        return improviseItems;
    }
}
