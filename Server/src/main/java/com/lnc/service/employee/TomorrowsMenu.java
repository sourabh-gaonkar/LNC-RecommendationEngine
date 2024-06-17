package com.lnc.service.employee;

import com.lnc.DB.MenuRolloutQueries;
import com.lnc.service.sentimentAnalysis.SentimentAnalysis;
import com.lnc.utils.ToJson;
import java.util.List;
import java.util.Map;

public class TomorrowsMenu {
  public String getTomorrowsMenu() throws Exception {
    MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();
    List<Map<String, Object>> tomorrowsMenu = menuRolloutQueries.getTomorrowsMenu();
    if (tomorrowsMenu == null) {
      throw new Exception("No menu found for today");
    } else {
      System.out.println(tomorrowsMenu.size());
    }

    SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
    List<Map<String, Object>> updatedMenu = sentimentAnalysis.getSentimentAnalysis(tomorrowsMenu);
    if (updatedMenu == null) {
      throw new Exception("No reviews found for today's menu");
    } else {
      System.out.println(updatedMenu.size());
    }

    ToJson toJson = new ToJson();

    return toJson.codeTodaysMenu(updatedMenu);
  }
}
