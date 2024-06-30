package com.lnc.service.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.EmployeeOrderQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.utils.ConversionFromJson;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuVote {
  private final MenuQueries menuQueries;
  private final MenuRolloutQueries menuRolloutQueries;
  private final Logger logger;

  public MenuVote() {
    menuQueries = new MenuQueries();
    menuRolloutQueries = new MenuRolloutQueries();
    logger = Logger.getLogger(MenuVote.class.getName());
  }

  public String voteForMenu(String jsonData) {
    try {
      ConversionFromJson converter = new ConversionFromJson();
      Map<String, Object> employeeVotingData = converter.decodeVotedMenu(jsonData);

      String employeeId = (String) employeeVotingData.get("employeeID");
      if (employeeId == null) {
        throw new NullPointerException("Employee ID is null.");
      }

      List<String> votedMenu = (List<String>) employeeVotingData.get("votedItems");

      logger.log(Level.INFO, "Voted items: {0}", votedMenu);

      EmployeeOrderQueries employeeOrderQueries = new EmployeeOrderQueries();

      for (String item : votedMenu) {
        processVotedItem(item, employeeOrderQueries, employeeId);
      }

      return "Added your vote successfully.";
    } catch (JsonProcessingException | NullPointerException ex) {
      logger.log(Level.SEVERE, "Error while processing vote: {0}", ex.getMessage());
      return "Error while processing your vote.";
    } catch (SQLException ex) {
      logger.log(Level.SEVERE, "Database error: {0}", ex.getMessage());
      return "Server Error.";
    } catch (IllegalArgumentException ex) {
      logger.log(Level.WARNING, "Invalid item: {0}", ex.getMessage());
      return "Invalid item. " + ex.getMessage();
    }
  }

  private void processVotedItem(String item, EmployeeOrderQueries employeeOrderQueries, String employeeId) throws SQLException {
    if (!menuQueries.checkMenuItemPresent(item)) {
      throw new IllegalArgumentException(item + " not found in the menu.");
    }

    if (menuRolloutQueries.voteForItem(item)) {
      setEmployeeFeedbackCount(item, employeeOrderQueries, employeeId);
    } else {
      throw new SQLException("Error while voting for " + item);
    }
  }

  private void setEmployeeFeedbackCount(String item, EmployeeOrderQueries employeeOrderQueries, String employeeId) {
    if (employeeOrderQueries.isRowPresent(employeeId, item)) {
      if (employeeOrderQueries.addFeedbackCount(employeeId, item)) {
        logger.log(Level.INFO, "Feedback count added successfully for {0}", item);
      } else {
        logger.log(Level.WARNING, "Error while adding feedback count for {0}", item);
      }
    } else {
      if (employeeOrderQueries.addNewItemFeedbackValue(employeeId, item)) {
        logger.log(Level.INFO, "New item feedback value added successfully for {0}", item);
      } else {
        logger.log(Level.WARNING, "Error while adding new item feedback value for {0}", item);
      }
    }
  }
}
