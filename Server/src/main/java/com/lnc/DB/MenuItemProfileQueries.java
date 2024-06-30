package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MenuItemProfileQueries {
    private final Logger logger = Logger.getLogger(DiscardMenuQueries.class.getName());
    private Connection connection;

    public MenuItemProfileQueries() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.severe("Failed to connect to the database.\n" + e.getMessage());
        }
    }

    public Map<String, Object> getMenuItemProfile(String itemName) {
        Map<String, Object> profile = new HashMap<>();

        String query = "SELECT diet_type, spice_level, region, sweetness " +
                "FROM menu_item_profile mip " +
                "JOIN menu m ON mip.item_id = m.item_id " +
                "WHERE m.item_name = ?";

        try (PreparedStatement getItemProfileStmt = connection.prepareStatement(query)) {
            getItemProfileStmt.setString(1, itemName);

            try (ResultSet rs = getItemProfileStmt.executeQuery()) {
                if (rs.next()) {
                    profile.put("diet_type", rs.getString("diet_type"));
                    profile.put("spice_level", rs.getString("spice_level"));
                    profile.put("region", rs.getString("region"));
                    profile.put("sweetness", rs.getBoolean("sweetness"));
                }
            }
        } catch (SQLException e) {
            logger.severe("Failed to get menu item profile.\n" + e.getMessage());
        }

        return profile;
    }
}
