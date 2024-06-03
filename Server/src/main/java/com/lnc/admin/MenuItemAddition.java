package com.lnc.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DAO.Menu;
import com.lnc.json.FromJson;

import java.sql.SQLException;

public class MenuItemAddition {
    public String addMenuItem(String jsonData) throws Exception {
        FromJson jsonDecoder = new FromJson();
        MenuItem item = jsonDecoder.decodeMenuItem(jsonData);

        Menu menu = new Menu();
        if(!menu.checkMenuItemPresent(item.getItemName())){
            if(menu.addMenuItem(item)){
                return "Added item to menu.";
            }
        } else {
            return "Item already present in menu.";
        }
        return "Error adding menu item.";
    }
}
