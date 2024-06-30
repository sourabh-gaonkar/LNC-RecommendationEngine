package com.lnc.model;

import java.util.List;

public class DailyMenu {
  private final List<String> breakfastItems;
  private final List<String> lunchItems;
  private final List<String> snackItems;
  private final List<String> dinnerItems;

  public DailyMenu(
      List<String> breakfastItems,
      List<String> lunchItems,
      List<String> snackItems,
      List<String> dinnerItems) {
    this.breakfastItems = breakfastItems;
    this.lunchItems = lunchItems;
    this.snackItems = snackItems;
    this.dinnerItems = dinnerItems;
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
