package com.lnc.service.employee;

import com.lnc.DB.EmployeeOrderQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.utils.ConversionFromJson;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MenuVote {
  private final MenuQueries menu = new MenuQueries();
  private final MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();

  public MenuVote() throws SQLException {}

  public String voteForMenu(String jsonData) throws Exception {
    ConversionFromJson fromJson = new ConversionFromJson();
    Map<String, Object> employeeVotingData = fromJson.decodeVotedMenu(jsonData);
    String employeeId = (String) employeeVotingData.get("employeeID");
    List<String> votedMenu = (List<String>) employeeVotingData.get("votedItems");
    System.out.println("Voted items: " + votedMenu);

    EmployeeOrderQueries employeeOrderQueries = new EmployeeOrderQueries();

    for (String item : votedMenu) {
      setEmployeeFeedbackCount(item, employeeOrderQueries, employeeId);
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

    return "Added your vote successfully.";
  }

  private void setEmployeeFeedbackCount(String item, EmployeeOrderQueries employeeOrderQueries, String employeeId) throws Exception {
    if(employeeOrderQueries.isRowPresent(employeeId, item)){
      if(employeeOrderQueries.addFeedbackCount(employeeId, item)){
        System.out.println("Feedback count added successfully for " + item);
      } else {
        System.out.println("Error while adding feedback count for " + item);
      }
    } else {
      if(employeeOrderQueries.addNewItemFeedbackValue(employeeId, item)){
        System.out.println("New item feedback value added successfully for " + item);
      } else {
        System.out.println("Error while adding new item feedback value for " + item);
      }
    }
  }
}
