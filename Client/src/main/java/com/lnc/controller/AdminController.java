package com.lnc.controller;

import static java.lang.Thread.sleep;

import com.lnc.service.admin.MenuItemAddition;
import com.lnc.service.admin.MenuItemDeletion;
import com.lnc.service.admin.MenuItemUpdate;
import com.lnc.service.admin.MenuView;
import com.lnc.util.InputHandler;

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
            if (choice == 5) {
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
                System.out.println("Logging out...");
                sleep(5);
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
        System.out.println("5. Logout");
    }
}
