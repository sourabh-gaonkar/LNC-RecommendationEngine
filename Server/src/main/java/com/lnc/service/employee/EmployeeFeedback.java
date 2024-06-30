package com.lnc.service.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.EmployeeOrderQueries;
import com.lnc.DB.FeedbackQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.DB.UserDetailsQueries;
import com.lnc.model.Feedback;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Logger;

public class EmployeeFeedback {
  private final Logger logger = Logger.getLogger(EmployeeFeedback.class.getName());
  private final ConversionFromJson fromJson = new ConversionFromJson();
  private final EmployeeOrderQueries employeeOrderQueries = new EmployeeOrderQueries();
  private final MenuQueries menuQueries = new MenuQueries();
  private final UserDetailsQueries userDetailsQueries = new UserDetailsQueries();
  private final FeedbackQueries feedbackQueries = new FeedbackQueries();

  public String getEmployeeFeedback(String jsonData) {
    try{
      Feedback feedback = fromJson.decodeFeedback(jsonData);

      if (!isValidFeedback(feedback)) {
        return "Invalid menu item or employee ID.";
      }

      if (!employeeOrderQueries.isRowPresent(feedback)) {
        return "You have not voted for this item. You can give feedback once you vote for the item.";
      }

      int feedbacksLeft = employeeOrderQueries.getFeedbacksLeft(feedback);
      if (feedbacksLeft <= 0) {
        return "You have not voted for this item. You can give feedback once you vote for the item.";
      }

      return processFeedback(feedback) ? "Feedback added successfully." : "Unable to add feedback.";
    } catch (JsonProcessingException | NullPointerException ex) {
      logger.severe("Invalid JSON format.");
        return "Invalid JSON format.";
    }
  }

  private boolean isValidFeedback(Feedback feedback) {
    return menuQueries.checkMenuItemPresent(feedback.getMenuItem()) &&
            userDetailsQueries.validateEmployeeID(feedback.getEmployeeID());
  }

  private boolean processFeedback(Feedback feedback) {
    if (!feedbackQueries.addFeedback(feedback)) {
      return false;
    }

    if (!employeeOrderQueries.subtractFeedbackCount(feedback)) {
      logger.warning("Unable to subtract feedback count.");
    }

    return true;
  }
}
