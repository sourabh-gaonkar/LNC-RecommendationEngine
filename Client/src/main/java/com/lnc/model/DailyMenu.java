package com.lnc.model;

import java.util.List;

public class DailyMenu {
    private List<String> breakfastItems;
    private List<String> lunchItems;
    private List<String> snackItems;
    private List<String> dinnerItems;

    public DailyMenu(List<String> breakfastItems, List<String> lunchItems, List<String> snackItems, List<String> dinnerItems) {
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
