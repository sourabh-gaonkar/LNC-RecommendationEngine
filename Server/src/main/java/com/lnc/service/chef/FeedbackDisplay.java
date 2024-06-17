package com.lnc.service.chef;

import com.lnc.DB.FeedbackQueries;
import com.lnc.DB.Menu;
import com.lnc.utils.FromJson;
import com.lnc.utils.ToJson;
import java.util.List;
import java.util.Map;

public class FeedbackDisplay {
  public String displayFeedback(String jsonData) throws Exception {
    FromJson fromJson = new FromJson();
    String itemName = fromJson.getJsonValue("itemName", jsonData);

    Menu menu = new Menu();
    if (menu.checkMenuItemPresent(itemName)) {
      FeedbackQueries feedbackQueries = new FeedbackQueries();
      List<Map<String, Object>> feedbackList = feedbackQueries.viewFeedback(itemName);
      Map<String, Object> weeklyStat = feedbackQueries.getWeeklyStats(itemName);
      Map<String, Object> overallStat = feedbackQueries.getOverallStats(itemName);

      ToJson toJson = new ToJson();
      return toJson.codeFeedbacks(feedbackList, weeklyStat, overallStat);
    }

    return "Invalid item name.";
  }
}
