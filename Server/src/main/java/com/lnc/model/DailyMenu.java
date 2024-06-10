package com.lnc.model;

import java.util.List;

public class DailyMenu {
    private List<String> breakfastItems;
    private List<String> lunchItems;
    private List<String> snackItems;
    private List<String> dinnerItems;

    public List<String> getBreakfastItems() {
        return breakfastItems;
    }

    public void setBreakfastItems(List<String> breakfastItems) {
        this.breakfastItems = breakfastItems;
    }

    public List<String> getLunchItems() {
        return lunchItems;
    }

    public void setLunchItems(List<String> lunchItems) {
        this.lunchItems = lunchItems;
    }

    public List<String> getSnackItems() {
        return snackItems;
    }

    public void setSnackItems(List<String> snackItems) {
        this.snackItems = snackItems;
    }

    public List<String> getDinnerItems() {
        return dinnerItems;
    }

    public void setDinnerItems(List<String> dinnerItems) {
        this.dinnerItems = dinnerItems;
    }
}

