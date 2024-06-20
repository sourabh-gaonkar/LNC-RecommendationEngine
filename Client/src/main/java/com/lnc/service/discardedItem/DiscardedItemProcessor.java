package com.lnc.service.discardedItem;

import com.lnc.util.InputHandler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscardedItemProcessor {
    private static final Logger logger = Logger.getLogger(DiscardedItemProcessor.class.getName());
    private final DiscardItemDisplay discardItemDisplay;

    public DiscardedItemProcessor() {
        this.discardItemDisplay = new DiscardItemDisplay();
    }

    public void processDiscardedItems() {
        try {
            List<String> discardedItems = discardItemDisplay.displayDiscardItems();
            if (discardedItems == null || discardedItems.isEmpty()) {
                System.out.println("No discarded items to display.");
                return;
            }

            System.out.println("\nSelect item to view functions.");
            int itemChoice = getValidChoice(discardedItems.size());

            String discardedItem = discardedItems.get(itemChoice - 1);

            displayOptions();
            int actionChoice = getValidChoice(2);
            processActionChoice(actionChoice, discardedItem);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O error while processing discarded items", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error while processing discarded items", e);
        }
    }

    private int getValidChoice(int maxChoice) throws IOException {
        int choice;
        do {
            choice = InputHandler.getInt("Enter your choice: ");
            if (choice < 1 || choice > maxChoice) {
                System.out.println("Invalid choice. Please try again.\n");
            }
        } while (choice < 1 || choice > maxChoice);
        return choice;
    }

    private void displayOptions() {
        System.out.println("\n1. Delete the item.");
        System.out.println("2. Improvise the item.\n");
    }

    private void processActionChoice(int actionChoice, String discardedItem) {
        switch (actionChoice) {
            case 1:
                new DiscardedItemDeletion().deleteDiscardedItem(discardedItem);
                break;
            case 2:
                new DiscardedItemImprovisation().improviseDiscardedItem(discardedItem);
                break;
            default:
                System.out.println("Invalid choice. Please try again.\n");
        }
    }
}
