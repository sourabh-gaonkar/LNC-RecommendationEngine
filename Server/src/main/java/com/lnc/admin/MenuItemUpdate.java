package com.lnc.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DAO.Menu;
import com.lnc.json.FromJson;

import java.sql.SQLException;

public class MenuItemUpdate {
    public String updateMenuItem(String jsonData) throws Exception {
        FromJson jsonDecoder = new FromJson();
        MenuItem item = jsonDecoder.decodeMenuItem(jsonData);

        Menu menu = new Menu();
        if(menu.checkMenuItemPresent(item.getItemName())) {
            if(menu.updateMenuItem(item)){
                return "Updated menu item.";
            }
        } else {
            return "Item not found.";
        }

        return "Error updating menu item.";
    }
}
