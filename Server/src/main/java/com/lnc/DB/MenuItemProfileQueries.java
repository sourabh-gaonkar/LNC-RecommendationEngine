package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.MenuItemProfile;

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

    public boolean addMenuItemProfile(MenuItemProfile menuItemProfile, int itemId) {
        String query = "INSERT INTO menu_item_profile (item_id, diet_type, spice_level, region, sweetness) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement addMenuItemProfileStmt = connection.prepareStatement(query)) {
            addMenuItemProfileStmt.setInt(1, itemId);
            addMenuItemProfileStmt.setString(2, menuItemProfile.getDietType());
            addMenuItemProfileStmt.setString(3, menuItemProfile.getSpiceLevel());
            addMenuItemProfileStmt.setString(4, menuItemProfile.getRegion());
            addMenuItemProfileStmt.setBoolean(5, menuItemProfile.isSweet());

            return addMenuItemProfileStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Failed to add menu item profile.\n" + e.getMessage());
            return false;
        }
    }

    public boolean updateMenuItemProfile(MenuItemProfile menuItemProfile, int itemId) {
        String query = "UPDATE menu_item_profile SET diet_type = ?, spice_level = ?, region = ?, sweetness = ? WHERE item_id = ?";

        try (PreparedStatement updateMenuItemProfileStmt = connection.prepareStatement(query)) {
            updateMenuItemProfileStmt.setString(1, menuItemProfile.getDietType());
            updateMenuItemProfileStmt.setString(2, menuItemProfile.getSpiceLevel());
            updateMenuItemProfileStmt.setString(3, menuItemProfile.getRegion());
            updateMenuItemProfileStmt.setBoolean(4, menuItemProfile.isSweet());
            updateMenuItemProfileStmt.setInt(5, itemId);

            return updateMenuItemProfileStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Failed to update menu item profile.\n" + e.getMessage());
            return false;
        }
    }
}
