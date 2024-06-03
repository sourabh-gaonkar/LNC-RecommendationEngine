package com.lnc.app;

import com.lnc.admin.*;
import com.lnc.auth.Registration;
import com.lnc.auth.authentication;

import java.sql.SQLException;

public class Redirection {
    private String response = null;
    public String redirect(String request) throws Exception {
        String[] parts = request.split("&");
        String path = parts[0];
        String data = parts[1];

        if(path.equalsIgnoreCase("/login")) {
            authentication auth = new authentication();
            response = auth.authenticate(data);
        }

        if(path.equalsIgnoreCase("/register")){
            Registration register = new Registration();
            response = register.addUser(data);
        }

        if(path.equalsIgnoreCase("/admin/addItem")){
            MenuItemAddition item = new MenuItemAddition();
            response = item.addMenuItem(data);
        }

        if(path.equalsIgnoreCase("/admin/deleteItem")){
            MenuItemDeletion item = new MenuItemDeletion();
            response = item.deleteMenuItem(data);
        }

        if(path.equalsIgnoreCase("/admin/viewItems")){
            MenuItemDisplay items = new MenuItemDisplay();
            response = items.displayMenu();
        }

        if(path.equalsIgnoreCase("/admin/updateItem")){
            MenuItemUpdate menuItemUpdate = new MenuItemUpdate();
            response = menuItemUpdate.updateMenuItem(data);
        }

        if(path.equalsIgnoreCase("/employee/feedback")){

        }

        return response;
    }
}
