package com.lnc.service.regiteration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.model.MenuItem;
import com.lnc.model.MenuItemProfile;
import com.lnc.service.MenuItemProfileFetcher;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;

public class MenuItemProfileUpdater {
    public void updateMenuItemProfile() throws IOException {
        String itemName = getItemName();

        MenuItemProfileFetcher menuItemProfileFetcher = new MenuItemProfileFetcher();
        MenuItemProfile menuItemProfile = menuItemProfileFetcher.getMenuItemProfile(itemName);

        ToJsonConversion jsonCoder = new ToJsonConversion();
        String menuItemProfileRequest = jsonCoder.codeMenuItemProfile(menuItemProfile, "/admin/updateItemProfile");
        String menuItemProfileResponse = ServerConnection.requestServer(menuItemProfileRequest);
        System.out.println("Response: " + menuItemProfileResponse);
    }

    private String getItemName() throws IOException {
        String itemName;
        do {
            itemName = InputHandler.getString("\nEnter menu item name: ");
        } while (itemName.isEmpty());
        return itemName;
    }
}
