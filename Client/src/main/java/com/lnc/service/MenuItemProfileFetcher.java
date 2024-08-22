package com.lnc.service;

import com.lnc.model.MenuItemProfile;
import com.lnc.util.InputHandler;

import java.io.IOException;

public class MenuItemProfileFetcher {
    public MenuItemProfile getMenuItemProfile(String itemName) throws IOException {
        int dietPreferenceCode = getDietTypeCode();
        int spiceLevelCode = getSpiceLevelCode();
        int regionalPreferenceCode = getRegionCode();
        boolean isSweet = getSweetness();

        MenuItemProfile menuItemProfile = new MenuItemProfile();
        menuItemProfile.setItemName(itemName);
        menuItemProfile.setDietType(dietPreferenceCode);
        menuItemProfile.setSpiceLevel(spiceLevelCode);
        menuItemProfile.setRegion(regionalPreferenceCode);
        menuItemProfile.setSweet(isSweet);

        return menuItemProfile;
    }

    private int getDietTypeCode() throws IOException {
        int dietTypeCode;
        while (true) {
            dietTypeCode = InputHandler.getInt("\nSelect your diet preference:\n1. Veg\n2. Egg\n3. Non-Veg\nEnter your choice: ");
            if (dietTypeCode < 1 || dietTypeCode > 3) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                continue;
            }
            break;
        }
        return dietTypeCode;
    }

    private int getSpiceLevelCode() throws IOException {
        int spiceLevelCode;
        while (true) {
            spiceLevelCode = InputHandler.getInt("\nSelect your spice level preference:\n1. High\n2. Medium\n3. Low\nEnter your choice: ");
            if (spiceLevelCode < 1 || spiceLevelCode > 3) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                continue;
            }
            break;
        }
        return spiceLevelCode;
    }

    private int getRegionCode() throws IOException {
        int regionCode;
        while (true) {
            regionCode = InputHandler.getInt("\nSelect your regional preference:\n1. South Indian\n2. North Indian\n3. Pan-Asian\nEnter your choice: ");
            if (regionCode < 1 || regionCode > 3) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                continue;
            }
            break;
        }
        return regionCode;
    }

    private boolean getSweetness() throws IOException {
        String sweetness;
        while (true) {
            sweetness = InputHandler.getString("\nDo you have a sweet tooth? (yes/no): ");
            if (!sweetness.equalsIgnoreCase("yes") && !sweetness.equalsIgnoreCase("no")) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                continue;
            }
            break;
        }
        return sweetness.equalsIgnoreCase("yes");
    }
}
