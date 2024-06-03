package com.lnc.admin;

import com.lnc.DAO.Menu;
import com.lnc.json.ToJson;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MenuItemDisplay {
    public String displayMenu() throws Exception {
        Menu menu = new Menu();
        List<Map<String, Object>> items = menu.viewMenuItems();

        ToJson jsonCoder = new ToJson();
        return jsonCoder.codeAllMenuItems(items);
    }
}
