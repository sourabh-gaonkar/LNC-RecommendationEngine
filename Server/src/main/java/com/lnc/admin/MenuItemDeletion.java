package com.lnc.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DAO.Menu;
import com.lnc.json.FromJson;

import java.sql.SQLException;

public class MenuItemDeletion {
    public String deleteMenuItem(String jsonData) throws Exception {
        FromJson jsonDecoder = new FromJson();
        String itemName = jsonDecoder.getJsonValue("itemName", jsonData);

        Menu menu = new Menu();
        if(menu.checkMenuItemPresent(itemName))  {
            if(menu.deleteMenuItem(itemName)){
                return "Deleted Item Successfully";
            }
        }
        return "Error deleting menu item..";
    }
}
