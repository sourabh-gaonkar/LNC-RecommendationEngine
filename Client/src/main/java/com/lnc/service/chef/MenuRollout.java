package com.lnc.service.chef;

import com.lnc.connection.ServerConnection;
import com.lnc.model.DailyMenu;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuRollout {
  public void rolloutMenu() throws Exception {
    List<String> breakfastItems = collectItems("breakfast", 3);
    List<String> lunchItems = collectItems("lunch", 4);
    List<String> snackItems = collectItems("snack", 4);
    List<String> dinnerItems = collectItems("dinner", 4);

    DailyMenu dailyMenu = new DailyMenu(breakfastItems, lunchItems, snackItems, dinnerItems);

    ToJsonConversion toJson = new ToJsonConversion();
    String request = toJson.codeDailyMenu(dailyMenu);

    String response = ServerConnection.requestServer(request);

    System.out.println(response);
  }

  private List<String> collectItems(String mealType, int itemCount) throws IOException {
    List<String> items = new ArrayList<>();
    System.out.print("\nEnter " + itemCount + " " + mealType + " items:\n");
    for (int i = 0; i < itemCount; i++) {
      items.add(getItemName(mealType + " item " + (i + 1) + ": "));
    }
    return items;
  }

  private String getItemName(String prompt) throws IOException {
    String itemName = InputHandler.getString(prompt);
    if (itemName.isEmpty()) {
      System.out.println("Item name cannot be empty\n");
      itemName = getItemName(prompt);
    }
    return itemName;
  }
}
