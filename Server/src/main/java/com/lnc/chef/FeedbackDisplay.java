package com.lnc.chef;

import com.lnc.DAO.FeedbackQueries;
import com.lnc.DAO.ItemRating;
import com.lnc.DAO.Menu;
import com.lnc.json.FromJson;
import com.lnc.json.ToJson;

import java.util.List;
import java.util.Map;

public class FeedbackDisplay {
  public String displayFeedback(String jsonData) throws Exception {
      FromJson fromJson = new FromJson();
      String itemName = fromJson.getJsonValue("itemName", jsonData);

      Menu menu = new Menu();
      if(menu.checkMenuItemPresent(itemName)){
          FeedbackQueries feedbackQueries = new FeedbackQueries();
          List<Map<String, Object>> feedbackList = feedbackQueries.viewFeedback(itemName);

          ItemRating itemRating = new ItemRating();
          Map<String, Object> weeklyStat = itemRating.getWeeklyStats(itemName);
          Map<String, Object> overallStat = itemRating.getOverallStats(itemName);


          ToJson toJson = new ToJson();
          return toJson.codeFeedbacks(feedbackList, weeklyStat, overallStat);
      }

      return "Invalid item name.";
    }
}
