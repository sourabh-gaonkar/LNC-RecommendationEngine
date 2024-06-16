package com.lnc.service.chef;

import com.lnc.DB.Menu;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.DB.NotificationQueries;
import com.lnc.model.DailyMenu;
import com.lnc.utils.FromJson;
import java.sql.SQLException;

public class RolloutMenu {
  private final Menu menuQueries = new Menu();
  private final MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();
  private final NotificationQueries notificationQueries = new NotificationQueries();

  public RolloutMenu() throws SQLException {}

  public String rolloutMenu(String jsonData) throws Exception {
    FromJson fromJson = new FromJson();
    DailyMenu dailyMenu = fromJson.decodeDailyMenu(jsonData);

    for (String item : dailyMenu.getBreakfastItems()) {
      if (!menuQueries.checkMenuItemPresent(item)) {
        return item + " not present in the menu";
      }
    }

    for (String item : dailyMenu.getLunchItems()) {
      if (!menuQueries.checkMenuItemPresent(item)) {
        return item + " not present in the menu";
      }
    }

    for (String item : dailyMenu.getSnackItems()) {
      if (!menuQueries.checkMenuItemPresent(item)) {
        return item + " not present in the menu";
      }
    }

    for (String item : dailyMenu.getDinnerItems()) {
      if (!menuQueries.checkMenuItemPresent(item)) {
        return item + " not present in the menu";
      }
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
  }
}
