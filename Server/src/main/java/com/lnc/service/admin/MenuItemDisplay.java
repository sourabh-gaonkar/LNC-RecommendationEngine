package com.lnc.service.admin;

import com.lnc.DB.MenuQueries;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MenuItemDisplay {
  private final Logger logger = Logger.getLogger(MenuItemDisplay.class.getName());
  public String displayMenu() throws Exception {
    MenuQueries menu = new MenuQueries();
    List<Map<String, Object>> items = menu.viewMenuItems();

    if(items.isEmpty()) {
      logger.info("No menu items found.");
      return null;
    }

    ConversionToJson jsonCoder = new ConversionToJson();
    return jsonCoder.codeAllMenuItems(items);
  }
}
