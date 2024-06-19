package com.lnc.service.employee;

import com.lnc.DB.FeedbackQueries;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.service.sentimentAnalysis.SentimentAnalysis;
import com.lnc.utils.ConversionToJson;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TodaysMenu {
  private final FeedbackQueries feedbackQueries = new FeedbackQueries();

    public TodaysMenu() throws SQLException {
    }

    public String getTodaysMenu() throws Exception {
    MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();
    List<Map<String, Object>> todaysMenu = menuRolloutQueries.getTodaysMenu();
    if (todaysMenu == null) {
      throw new Exception("No menu found for today");
    } else {
      System.out.println(todaysMenu.size());
    }

    SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
    List<Map<String, Object>> updatedMenu = sentimentAnalysis.getSentimentAnalysis(todaysMenu);
    if (updatedMenu == null) {
      throw new Exception("No reviews found for today's menu");
    } else {
      System.out.println(updatedMenu.size());
    }

    ConversionToJson toJson = new ConversionToJson();
    return toJson.codeTodaysMenu(updatedMenu);
  }
}
