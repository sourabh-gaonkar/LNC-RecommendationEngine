package com.lnc.service.admin;

import com.lnc.DB.MenuQueries;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MenuItemDisplay {
  private final Logger logger = Logger.getLogger(MenuItemDisplay.class.getName());
  private final MenuQueries menu = new MenuQueries();
  private final ConversionToJson jsonCoder = new ConversionToJson();

  public String displayMenu() throws Exception {
    List<Map<String, Object>> items = menu.viewMenuItems();

    if(items.isEmpty()) {
      logger.info("No menu items found.");
      return null;
    }

    return jsonCoder.codeAllMenuItems(items);
  }
}
