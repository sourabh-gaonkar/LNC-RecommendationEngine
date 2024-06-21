package com.lnc.controller;

import com.lnc.service.UserLogout;
import com.lnc.service.admin.MenuItemAddition;
import com.lnc.service.admin.MenuItemDeletion;
import com.lnc.service.admin.MenuItemUpdate;
import com.lnc.service.admin.MenuView;
import com.lnc.service.discardedItem.DiscardedItemProcessor;
import com.lnc.util.InputHandler;

import static java.lang.Thread.sleep;

public class AdminController {
    private final String name;
    private final String employeeID;

    public AdminController(String name, String employeeID) {
        this.name = name;
        this.employeeID = employeeID;
    }

    public void runHomepage() throws Exception {
        System.out.println("\nWelcome " + name);
        run();
    }

    private void run() throws Exception {
        while (true) {
            displayOptions();
            int choice = InputHandler.getInt("Enter your choice: ");
            processOption(choice);
            if (choice == 6) {
                break;
            }
        }
    }

  private void processOption(int choice) throws Exception {
        switch (choice) {
            case 1:
                MenuItemAddition menuItemAddition = new MenuItemAddition();
                menuItemAddition.addMenuItem();
                break;
            case 2:
                MenuItemUpdate menuItemUpdate = new MenuItemUpdate();
                menuItemUpdate.updateMenuItem();
                break;
            case 3:
                MenuItemDeletion menuItemDeletion = new MenuItemDeletion();
                menuItemDeletion.deleteMenuItem();
                break;
            case 4:
                MenuView menuItemDisplay = new MenuView();
                menuItemDisplay.displayMenu();
                break;
            case 5:
                DiscardedItemProcessor discardedItemProcessor = new DiscardedItemProcessor();
                discardedItemProcessor.processDiscardedItems();
                break;
            case 6:
                UserLogout userLogout = new UserLogout();
                boolean isLoggedOut = userLogout.logout(employeeID);
                if (isLoggedOut) {
                    System.out.println("Logging out...");
                    sleep(5);
                } else {
                    choice = 0;
                    System.out.println("Error in logging out");
                }
                break;
            default:
                System.out.println("Invalid choice");
                run();
                break;
        }
    }

    private void displayOptions() {
        System.out.println("\n1. Add Menu Item");
        System.out.println("2. Update Menu Item");
        System.out.println("3. Delete Menu Item");
        System.out.println("4. View Menu Items");
        System.out.println("5. View Discard Items. (Available 1st of every month)");
        System.out.println("6. Logout");
    }
}
