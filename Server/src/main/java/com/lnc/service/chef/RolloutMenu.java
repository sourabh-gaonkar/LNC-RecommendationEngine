package com.lnc.service.chef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.MenuQueries;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.DB.NotificationQueries;
import com.lnc.model.DailyMenu;
import com.lnc.utils.ConversionFromJson;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class RolloutMenu {
  private static final Logger logger = Logger.getLogger(RolloutMenu.class.getName());
  private final MenuQueries menuQueries = new MenuQueries();
  private final MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();
  private final NotificationQueries notificationQueries = new NotificationQueries();

  public String rolloutMenu(String jsonData) {
    try {
      DailyMenu dailyMenu = new ConversionFromJson().decodeDailyMenu(jsonData);
      String validationError = validateMenuItems(dailyMenu);

      if (validationError != null) {
        return validationError;
      }

      if (menuRolloutQueries.rolloutMenu(dailyMenu)) {
        if (notificationQueries.insertRolloutNotification()) {
          return "Menu rolled out successfully and notifications sent to all employees";
        } else {
          return "Menu rollout successful but failed to send notifications";
        }
      } else {
        return "Menu rollout failed";
      }
    } catch (JsonProcessingException | NullPointerException | SQLException ex) {
      logger.severe("Error rolling out menu: " + ex.getMessage());
      return "Menu rollout failed";
    }
  }

  private String validateMenuItems(DailyMenu dailyMenu) throws SQLException {
    if (!areItemsInMenu(dailyMenu.getBreakfastItems())) {
      return "One or more breakfast items not present in the menu";
    }
    if (!areItemsInMenu(dailyMenu.getLunchItems())) {
      return "One or more lunch items not present in the menu";
    }
    if (!areItemsInMenu(dailyMenu.getSnackItems())) {
      return "One or more snack items not present in the menu";
    }
    if (!areItemsInMenu(dailyMenu.getDinnerItems())) {
      return "One or more dinner items not present in the menu";
    }
    return null;
  }

  private boolean areItemsInMenu(List<String> items) throws SQLException {
    for (String item : items) {
      if (!menuQueries.checkMenuItemPresent(item)) {
        return false;
      }
    }
    return true;
  }
}
