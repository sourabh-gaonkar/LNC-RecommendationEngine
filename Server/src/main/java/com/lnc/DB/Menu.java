package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Menu {
    Logger logger = Logger.getLogger(Menu.class.getName());
    private Connection connection;

    public Menu() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException ex) {
            logger.severe("Failed to connect to database: " + ex.getMessage());
        }
    }

    public boolean addMenuItem(MenuItem item) {
        boolean isMenuItemAdded = false;

        String query = "INSERT INTO menu (item_name, price, availability, category) VALUES (?, ?, ?, ?)";

        try(PreparedStatement addMenuItemStmt = connection.prepareStatement(query)) {
            addMenuItemStmt.setString(1, item.getItemName());
            addMenuItemStmt.setDouble(2, item.getPrice());
            addMenuItemStmt.setBoolean(3, item.isAvailable());
            addMenuItemStmt.setString(4, item.getCategory());

            isMenuItemAdded = addMenuItemStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.severe("Failed to add menu item: " + ex.getMessage());
        }
        return isMenuItemAdded;
    }

    public boolean updateMenuItem(MenuItem item) {
        boolean isMenuItemUpdated = false;

        String query = "UPDATE menu SET price =?, availability =? WHERE item_name =?";

        try(PreparedStatement updateMenuItemStmt = connection.prepareStatement(query)) {
            updateMenuItemStmt.setDouble(1, item.getPrice());
            updateMenuItemStmt.setBoolean(2, item.isAvailable());
            updateMenuItemStmt.setString(3, item.getItemName());

            isMenuItemUpdated = updateMenuItemStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.severe("Failed to update menu item: " + ex.getMessage());
        }
        return isMenuItemUpdated;
    }

    public boolean deleteMenuItem(String item) {

        String query = "{CALL delete_menu_item(?)}";
        try (CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setString(1, item);

            callableStatement.execute();
        } catch (SQLException ex) {
            logger.severe("Failed to delete menu item: " + ex.getMessage());
        }
        return !checkMenuItemPresent(item);
    }

    public boolean checkMenuItemPresent(String item) {
        boolean isMenuItemPresent = false;

        String query = "SELECT * FROM menu WHERE item_name =?";

        try(PreparedStatement checkMenuItemPresentStmt = connection.prepareStatement(query)) {
            checkMenuItemPresentStmt.setString(1, item);

            ResultSet rs = checkMenuItemPresentStmt.executeQuery();
            if(rs.next()) {
                isMenuItemPresent = true;
            }
        } catch (SQLException ex) {
            logger.severe("Failed to check if menu item is present: " + ex.getMessage());
        }

        return isMenuItemPresent;
    }

    public List<Map<String, Object>> viewMenuItems() {
        List<Map<String, Object>> menuList = new ArrayList<>();

        String query = "SELECT item_name, price, availability, category FROM menu";

        try(PreparedStatement viewMenuItemsStmt = connection.prepareStatement(query)) {
            ResultSet rs = viewMenuItemsStmt.executeQuery();

            while(rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("item_name", rs.getString("item_name"));
                item.put("price", rs.getBigDecimal("price"));
                item.put("availability", rs.getBoolean("availability"));
                item.put("category", rs.getString("category"));
                menuList.add(item);
            }
        } catch (SQLException ex) {
            logger.severe("Failed to view menu items: " + ex.getMessage());
        }

        return menuList;
    }

    public int getItemID(String menuItem) {
        int itemID = 0;
        String query = "SELECT item_id FROM menu WHERE item_name =?";
        try(PreparedStatement getItemIDStmt = connection.prepareStatement(query)) {
            getItemIDStmt.setString(1, menuItem);
            ResultSet rs = getItemIDStmt.executeQuery();
            if(rs.next()) {
                itemID = rs.getInt("item_id");
            }
        } catch (SQLException ex) {
            logger.severe("Error while getting item ID: " + ex.getMessage());
        }
        return itemID;
    }}
