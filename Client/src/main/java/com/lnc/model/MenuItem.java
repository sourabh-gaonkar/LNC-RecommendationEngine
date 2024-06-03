package com.lnc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItem {
    @JsonProperty("item_name")
    private String itemName;
    private Double price;
    private Boolean availability;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
