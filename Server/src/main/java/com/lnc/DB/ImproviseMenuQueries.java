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

public class ImproviseMenuQueries {
    private final Logger logger = Logger.getLogger(ImproviseMenuQueries.class.getName());
    private Connection connection;

    public ImproviseMenuQueries() {
        try {
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to connect to the database.", e);
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
            logger.log(Level.SEVERE, "Failed to add item to improvise menu.", ex);
        }
        return isItemAdded;
    }

    public List<String> getAllImproviseList() {
        List<String> improviseItems = new ArrayList<>();
        String query = "SELECT item_name FROM menu WHERE item_id IN (SELECT item_id FROM improvise_menu)";

        try (PreparedStatement getImproviseItemStmt = connection.prepareStatement(query)) {
            ResultSet resultSet = getImproviseItemStmt.executeQuery();
            while (resultSet.next()) {
                improviseItems.add(resultSet.getString("item_name"));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to get improvise items.", ex);
        }
        return improviseItems;
    }
}