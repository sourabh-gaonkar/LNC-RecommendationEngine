package com.lnc.service.chef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.FeedbackQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FeedbackDisplay {
  private final Logger logger = Logger.getLogger(FeedbackDisplay.class.getName());

  public String displayFeedback(String jsonData) {
    String itemName = getItemNameFromJson(jsonData);
    if (itemName == null) {
      return "Invalid item name.";
    }

    if (!isMenuItemPresent(itemName)) {
      return "Invalid item name.";
    }

    return generateFeedbackResponse(itemName);
  }

  private String getItemNameFromJson(String jsonData) {
    try {
      ConversionFromJson fromJson = new ConversionFromJson();
      return fromJson.getJsonValue("itemName", jsonData);
    } catch (JsonProcessingException | NullPointerException e) {
      logger.severe("Error parsing JSON: " + e.getMessage());
      return null;
    }
  }

  private boolean isMenuItemPresent(String itemName) {
    MenuQueries menu = new MenuQueries();
    return menu.checkMenuItemPresent(itemName);
  }

  private String generateFeedbackResponse(String itemName) {
    FeedbackQueries feedbackQueries = new FeedbackQueries();

    List<Map<String, Object>> feedbackList = feedbackQueries.viewFeedback(itemName);
    if (feedbackList.isEmpty()) {
      return "No feedbacks available for this item.";
    }

    Map<String, Object> weeklyStat = feedbackQueries.getWeeklyStats(itemName);
    if (weeklyStat.isEmpty()) {
      return "No weekly stats available for this item.";
    }

    Map<String, Object> overallStat = feedbackQueries.getOverallStats(itemName);
    if (overallStat.isEmpty()) {
      return "No overall stats available for this item.";
    }

    return convertFeedbacksToJson(feedbackList, weeklyStat, overallStat);
  }

  private String convertFeedbacksToJson(List<Map<String, Object>> feedbackList,
                                        Map<String, Object> weeklyStat,
                                        Map<String, Object> overallStat) {
    try {
      ConversionToJson toJson = new ConversionToJson();
      return toJson.codeFeedbacks(feedbackList, weeklyStat, overallStat);
    } catch (JsonProcessingException | NullPointerException e) {
      logger.severe("Error converting feedbacks to JSON: " + e.getMessage());
      return "Error processing feedback data.";
    }
  }
}