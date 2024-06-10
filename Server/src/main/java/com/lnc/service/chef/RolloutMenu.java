package com.lnc.service.chef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.Menu;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.model.DailyMenu;
import com.lnc.utils.FromJson;

import java.sql.SQLException;

public class RolloutMenu {
    Menu menuQueries = new Menu();
    MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();

    public RolloutMenu() throws SQLException {
    }

    public String rolloutMenu(String jsonData) throws Exception {
        FromJson fromJson = new FromJson();
        DailyMenu dailyMenu = fromJson.decodeDailyMenu(jsonData);

        for(String item : dailyMenu.getBreakfastItems()) {
            if(!menuQueries.checkMenuItemPresent(item)){
                return item + " not present in the menu";
            }
        }

        for(String item : dailyMenu.getLunchItems()) {
            if(!menuQueries.checkMenuItemPresent(item)){
                return item + " not present in the menu";
            }
        }

        for(String item : dailyMenu.getSnackItems()) {
            if(!menuQueries.checkMenuItemPresent(item)){
                return item + " not present in the menu";
            }
        }

        for(String item : dailyMenu.getDinnerItems()) {
            if(!menuQueries.checkMenuItemPresent(item)){
                return item + " not present in the menu";
            }
        }

        if (menuRolloutQueries.rolloutMenu(dailyMenu)){
            return "Menu rolled out successfully";
        } else {
            return "Menu rollout failed";
        }
    }
}
