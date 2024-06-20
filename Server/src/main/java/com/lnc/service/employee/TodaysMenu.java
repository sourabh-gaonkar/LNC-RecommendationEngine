package com.lnc.service.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.service.sentimentAnalysis.SentimentAnalysis;
import com.lnc.utils.ConversionToJson;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TodaysMenu {
    private final Logger logger = Logger.getLogger(TodaysMenu.class.getName());
    private final MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();
    private final SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
    private final ConversionToJson toJson = new ConversionToJson();

    public String getTodaysMenu() {
      List<Map<String, Object>> todaysMenu = menuRolloutQueries.getTodaysMenu();
      if (todaysMenu == null) {
        logger.severe("Null menu returned from database");
        return null;
      } else {
        System.out.println(todaysMenu.size());
      }

      List<Map<String, Object>> updatedMenu = sentimentAnalysis.getSentimentAnalysis(todaysMenu);
      if (updatedMenu == null) {
        logger.severe("Null menu returned from sentiment analysis");
        return null;
      } else {
        System.out.println(updatedMenu.size());
      }

      try{
        return toJson.codeTodaysMenu(updatedMenu);
      } catch (JsonProcessingException | NullPointerException e) {
        logger.severe("Error in converting to JSON");
        return null;
      }
    }
}
