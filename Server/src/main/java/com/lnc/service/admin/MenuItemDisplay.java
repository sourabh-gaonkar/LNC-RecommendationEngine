package com.lnc.service.admin;

import com.lnc.DB.MenuQueries;
import com.lnc.utils.ToJson;
import java.util.List;
import java.util.Map;

public class MenuItemDisplay {
  public String displayMenu() throws Exception {
    MenuQueries menu = new MenuQueries();
    List<Map<String, Object>> items = menu.viewMenuItems();

    ToJson jsonCoder = new ToJson();
    return jsonCoder.codeAllMenuItems(items);
  }
}
