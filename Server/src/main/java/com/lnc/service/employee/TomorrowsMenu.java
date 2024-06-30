package com.lnc.service.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.service.sentimentAnalysis.SentimentAnalysis;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TomorrowsMenu {
  private final Logger logger = Logger.getLogger(TomorrowsMenu.class.getName());
  private final ConversionToJson toJson = new ConversionToJson();
  private final SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
  private final MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();
  private final PreferenceSorter preferenceSorter = new PreferenceSorter();
  private final ConversionFromJson fromJson = new ConversionFromJson();

  public String getTomorrowsMenu(String jsonData) {
    try{
      List<Map<String, Object>> tomorrowsMenu = menuRolloutQueries.getTomorrowsMenu();
      if (tomorrowsMenu == null) {
        logger.severe("No menu found for tomorrow");
        return null;
      }

      String employeeId = fromJson.getJsonValue("employee_id", jsonData);
      List<Map<String, Object>> sortedMenu = preferenceSorter.sortMenuItems(tomorrowsMenu, employeeId);

      List<Map<String, Object>> updatedMenu = sentimentAnalysis.getSentimentAnalysis(tomorrowsMenu);
      if (updatedMenu == null) {
        logger.severe("Sentiment analysis failed");
        return null;
      }

      return toJson.codeTodaysMenu(updatedMenu);
    } catch (JsonProcessingException | NullPointerException e) {
      logger.severe("Failed to convert menu to JSON");
      return null;
    }
  }
}
