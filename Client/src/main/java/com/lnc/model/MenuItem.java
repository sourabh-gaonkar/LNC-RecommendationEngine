package com.lnc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItem {
  @JsonProperty("item_name")
  private String itemName;
  private Double price;
  private Boolean availability;
  private Category category;

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

  public Category getCategory() {
    return category;
  }

  public void setCategory(int categoryCode) {
    this.category = Category.fromInt(categoryCode);
  }

  public enum Category {
    BREAKFAST,
    LUNCH,
    SNACK,
    DINNER;

    public static Category fromInt(int categoryCode) {
      switch (categoryCode) {
        case 1:
          return BREAKFAST;
        case 2:
          return LUNCH;
        case 3:
          return SNACK;
        case 4:
          return DINNER;
        default:
          throw new IllegalArgumentException("Invalid category code: " + categoryCode);
      }
    }
  }
}
