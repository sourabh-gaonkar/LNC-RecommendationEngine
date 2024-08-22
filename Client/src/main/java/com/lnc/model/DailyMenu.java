package com.lnc.model;

import java.util.List;

public class DailyMenu {
  private final List<String> breakfastItems;
  private final List<String> lunchItems;
  private final List<String> snackItems;
  private final List<String> dinnerItems;

  public DailyMenu(List<List<String>> dailyMenuItems) {
    this.breakfastItems = dailyMenuItems.get(0);
    this.lunchItems = dailyMenuItems.get(1);
    this.snackItems = dailyMenuItems.get(2);
    this.dinnerItems = dailyMenuItems.get(3);
  }

  public List<String> getBreakfastItems() {
    return breakfastItems;
  }

  public List<String> getLunchItems() {
    return lunchItems;
  }

  public List<String> getSnackItems() {
    return snackItems;
  }

  public List<String> getDinnerItems() {
    return dinnerItems;
  }
}
