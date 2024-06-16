package com.lnc.DB;

import com.lnc.connection.JDBCConnection;
import com.lnc.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final Connection connection;

    public Menu() throws SQLException {
        JDBCConnection dbInstance = JDBCConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public boolean addMenuItem(MenuItem item) throws Exception {
        boolean isMenuItemAdded = false;

        String query = "INSERT INTO menu (item_name, price, availability, category) VALUES (?, ?, ?, ?)";

        try(PreparedStatement addMenuItemStmt = connection.prepareStatement(query)) {
            addMenuItemStmt.setString(1, item.getItemName());
            addMenuItemStmt.setDouble(2, item.getPrice());
            addMenuItemStmt.setBoolean(3, item.isAvailable());
            addMenuItemStmt.setString(4, item.getCategory());

            isMenuItemAdded = addMenuItemStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new Exception("\nFailed to add menu item.\n" + ex.getMessage());
        }
        return isMenuItemAdded;
    }

    public boolean updateMenuItem(MenuItem item) throws Exception {
        boolean isMenuItemUpdated = false;

        String query = "UPDATE menu SET price =?, availability =? WHERE item_name =?";

        try(PreparedStatement updateMenuItemStmt = connection.prepareStatement(query)) {
            updateMenuItemStmt.setDouble(1, item.getPrice());
            updateMenuItemStmt.setBoolean(2, item.isAvailable());
            updateMenuItemStmt.setString(3, item.getItemName());

            isMenuItemUpdated = updateMenuItemStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new Exception("\nFailed to update menu item.\n" + ex.getMessage());
        }
        return isMenuItemUpdated;
    }

    public boolean deleteMenuItem(String item) throws Exception {

        String query = "{CALL delete_menu_item(?)}";
        try (CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setString(1, item);

            callableStatement.execute();
        } catch (SQLException ex) {
            throw new Exception("\nFailed to delete menu item.\n" + ex.getMessage());
        }
        return !checkMenuItemPresent(item);
    }

    public boolean checkMenuItemPresent(String item) throws Exception {
        boolean isMenuItemPresent = false;

        String query = "SELECT * FROM menu WHERE item_name =?";

        try(PreparedStatement checkMenuItemPresentStmt = connection.prepareStatement(query)) {
            checkMenuItemPresentStmt.setString(1, item);

            ResultSet rs = checkMenuItemPresentStmt.executeQuery();
            if(rs.next()) {
                isMenuItemPresent = true;
            }
        } catch (SQLException ex) {
            throw new Exception("\nFailed to check.\n" + ex.getMessage());
        }

        return isMenuItemPresent;
    }

    public List<Map<String, Object>> viewMenuItems() throws Exception {
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
            throw new Exception("\nFailed to view menu items.\n" + ex.getMessage());
        }

        return menuList;
    }

    public int getItemID(String menuItem) throws Exception {
        int itemID = 0;
        String query = "SELECT item_id FROM menu WHERE item_name =?";
        try(PreparedStatement getItemIDStmt = connection.prepareStatement(query)) {
            getItemIDStmt.setString(1, menuItem);
            ResultSet rs = getItemIDStmt.executeQuery();
            if(rs.next()) {
                itemID = rs.getInt("item_id");
            }
        } catch (SQLException ex) {
            throw new Exception("\nFailed to get item ID.\n" + ex.getMessage());
        }
        return itemID;
    }}
