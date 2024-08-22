package com.lnc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItem {

  @JsonProperty("item_name")
  private String itemName;

  private double price;
  private boolean availability;
  private String category;

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean isAvailable() {
    return availability;
  }

  public void setAvailability(boolean availability) {
    this.availability = availability;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}
