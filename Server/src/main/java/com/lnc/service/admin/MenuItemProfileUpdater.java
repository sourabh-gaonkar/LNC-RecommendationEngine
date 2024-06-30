package com.lnc.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.MenuItemProfileQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.model.MenuItemProfile;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Logger;

public class MenuItemProfileUpdater {
    private final Logger logger = Logger.getLogger(MenuItemProfileAddition.class.getName());
    private final ConversionFromJson fromJson = new ConversionFromJson();
    private final MenuQueries menuQueries = new MenuQueries();
    private final MenuItemProfileQueries menuItemProfileQueries = new MenuItemProfileQueries();

    public String updateMenuItemProfile(String jsonData) {
        try {
            MenuItemProfile menuItemProfile = fromJson.decodeMenuItemProfile(jsonData);
            String itemName = menuItemProfile.getItemName();
            int itemId = menuQueries.getItemID(itemName);

            if(menuItemProfileQueries.updateMenuItemProfile(menuItemProfile, itemId)) {
                return "Updated menu item profile.";
            } else {
                return "Failed to update menu item profile.";
            }
        } catch (JsonProcessingException | NullPointerException ex) {
            logger.severe("Error in updating menu item profile: " + ex.getMessage());
            return "Error in updating menu item profile.";
        }
    }
}
