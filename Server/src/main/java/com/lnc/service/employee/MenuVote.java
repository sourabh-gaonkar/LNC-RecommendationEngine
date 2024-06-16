package com.lnc.service.employee;

import com.lnc.DB.Menu;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.utils.FromJson;
import java.sql.SQLException;
import java.util.List;

public class MenuVote {
  private final Menu menu = new Menu();
  private final MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();

  public MenuVote() throws SQLException {}

  public String voteForMenu(String jsonData) throws Exception {
    FromJson fromJson = new FromJson();
    List<String> votedMenu = fromJson.decodeVotedMenu(jsonData);

    for (String item : votedMenu) {
      if (menu.checkMenuItemPresent(item)) {
        if (menuRolloutQueries.voteForItem(item)) {
          continue;
        } else {
          return "Error while voting for " + item;
        }
      } else {
        return item + " not found in the menu.";
      }
    }

    return "Updated your votes successfully.";
  }
}
