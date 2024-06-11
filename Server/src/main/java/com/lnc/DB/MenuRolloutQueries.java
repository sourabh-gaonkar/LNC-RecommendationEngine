package com.lnc.DB;

import com.lnc.Connection.JDBCConnection;
import com.lnc.model.DailyMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MenuRolloutQueries {
    private final Connection connection;
    Menu menuQueries = new Menu();

    public MenuRolloutQueries() throws SQLException {
        JDBCConnection dbInstance = JDBCConnection.getInstance();
        connection = dbInstance.getConnection();
    }

    public boolean rolloutMenu(DailyMenu dailyMenu) {
        boolean isRolledOut = false;
        boolean isLunchAdded = false;
        boolean isSnackAdded = false;
        boolean isDinnerAdded = false;
        boolean isBFAdded = false;

        LocalDate currentDate = LocalDate.now();

        String query = "INSERT INTO menu_rollout (item_id, date, votes) VALUES (?, ?, 0)";

        try(PreparedStatement rolloutMenuStmt = connection.prepareStatement(query)) {
            for(String item : dailyMenu.getBreakfastItems()) {
                int itemID = menuQueries.getItemID(item);
                rolloutMenuStmt.setInt(1, itemID);
                rolloutMenuStmt.setString(2, currentDate.toString());
                isBFAdded = rolloutMenuStmt.executeUpdate() > 0;
            }

            for(String item : dailyMenu.getLunchItems()) {
                int itemID = menuQueries.getItemID(item);
                rolloutMenuStmt.setInt(1, itemID);
                rolloutMenuStmt.setString(2, currentDate.toString());
                isLunchAdded = rolloutMenuStmt.executeUpdate() > 0;
            }

            for(String item : dailyMenu.getSnackItems()) {
                int itemID = menuQueries.getItemID(item);
                rolloutMenuStmt.setInt(1, itemID);
                rolloutMenuStmt.setString(2, currentDate.toString());
                isSnackAdded = rolloutMenuStmt.executeUpdate() > 0;
            }

            for(String item : dailyMenu.getDinnerItems()) {
                int itemID = menuQueries.getItemID(item);
                rolloutMenuStmt.setInt(1, itemID);
                rolloutMenuStmt.setString(2, currentDate.toString());
                isDinnerAdded = rolloutMenuStmt.executeUpdate() > 0;
            }

            isRolledOut = isBFAdded && isLunchAdded && isSnackAdded && isDinnerAdded;
        } catch (SQLException ex) {
            System.out.println("\nFailed to roll out menu.\n" + ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return isRolledOut;
    }
}
