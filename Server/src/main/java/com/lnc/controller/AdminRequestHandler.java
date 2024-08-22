package com.lnc.controller;

import com.lnc.service.admin.*;

public class AdminRequestHandler implements RouteHandler {
    private final MenuItemAddition addItem = new MenuItemAddition();
    private final MenuItemProfileAddition addItemProfile = new MenuItemProfileAddition();
    private final MenuItemProfileUpdater updateItemProfile = new MenuItemProfileUpdater();
    private final MenuItemDeletion deleteItem = new MenuItemDeletion();
    private final MenuItemDisplay viewItems = new MenuItemDisplay();
    private final MenuItemUpdate updateItem = new MenuItemUpdate();

    @Override
    public String handle(String path, String data) throws Exception {
        return switch (path) {
            case "/admin/addItem" -> addItem.addMenuItem(data);
            case "/admin/addItemProfile" -> addItemProfile.addMenuItemProfile(data);
            case "/admin/updateItemProfile" -> updateItemProfile.updateMenuItemProfile(data);
            case "/admin/deleteItem" -> deleteItem.deleteMenuItem(data);
            case "/admin/viewItems" -> viewItems.displayMenu();
            case "/admin/updateItem" -> updateItem.updateMenuItem(data);
            default -> throw new IllegalArgumentException("Invalid path for AdminRoutes: " + path);
        };
    }
}
