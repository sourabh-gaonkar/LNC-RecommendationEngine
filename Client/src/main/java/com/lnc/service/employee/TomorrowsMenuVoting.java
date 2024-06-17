package com.lnc.service.employee;

import com.lnc.connection.ServerConnection;
import com.lnc.util.InputHandler;
import com.lnc.util.JsonDataFormat;
import com.lnc.util.ToJsonConversion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TomorrowsMenuVoting {
    public void voteForTomorrowsMenu() throws Exception {
        String apiPath = "/employee/tomorrowsMenu";
        String requestTomorrowsMenu = apiPath + "& ";

        String responseMenu = ServerConnection.requestServer(requestTomorrowsMenu);

        JsonDataFormat jsonDataFormat = new JsonDataFormat();
        List<String> menu = jsonDataFormat.printDaysMenu(responseMenu, "TOMORROW");

        System.out.println("\nEnter your choices.");
        int breakfastChoice = getChoice(3, "Breakfast");
        int lunchChoice = getChoice(4, "Lunch");
        int snackChoice = getChoice(4, "Snack");
        int dinnerChoice = getChoice(4, "Dinner");

        List<String> votedItemList = new ArrayList<>();
        votedItemList.add(menu.get(breakfastChoice - 1));
        votedItemList.add(menu.get(lunchChoice - 1));
        votedItemList.add(menu.get(snackChoice - 1));
        votedItemList.add(menu.get(dinnerChoice - 1));

        ToJsonConversion convertToJson = new ToJsonConversion();
        String requestVoting = convertToJson.codeVotedItems(votedItemList);

        String responseVoting = ServerConnection.requestServer(requestVoting);
        System.out.println(responseVoting);
    }

    private int getChoice(int maxChoice, String mealType) throws IOException {
        int startOption = 1;
        if(mealType.equalsIgnoreCase("Lunch")){
            startOption = 4;
        } else if(mealType.equalsIgnoreCase("Snack")){
            startOption = 8;
        } else if(mealType.equalsIgnoreCase("Dinner")){
            startOption = 12;
        }
        int choice = InputHandler.getInt("Enter your choice for " + mealType + ": ");
        while (choice < startOption || choice > (startOption + maxChoice - 1)) {
            System.out.println("Invalid choice. Please enter a valid choice.\n");
            choice = InputHandler.getInt("Enter your choice for " + mealType + ": ");
        }
        return choice;
    }
}
