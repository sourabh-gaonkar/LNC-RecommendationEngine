package com.lnc.service.employee;

import com.lnc.DB.EmployeeOrderQueries;
import com.lnc.DB.FeedbackQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.DB.UserDetailsQueries;
import com.lnc.model.Feedback;
import com.lnc.utils.FromJson;

public class EmployeeFeedback {
  public String getEmployeeFeedback(String jsonData) throws Exception {
    FromJson fromJson = new FromJson();
    Feedback feedback = fromJson.decodeFeedback(jsonData);

    EmployeeOrderQueries employeeOrderQueries = new EmployeeOrderQueries();
    if (employeeOrderQueries.isRowPresent(feedback)) {
      int feedbacksLeft = employeeOrderQueries.getFeedbacksLeft(feedback);
      if(feedbacksLeft > 0) {
        MenuQueries menu = new MenuQueries();
        UserDetailsQueries userDetails = new UserDetailsQueries();
        FeedbackQueries feedbackQueries = new FeedbackQueries();
        if (menu.checkMenuItemPresent(feedback.getMenuItem())
                && userDetails.validateEmployeeID(feedback.getEmployeeID())) {
          if (feedbackQueries.addFeedback(feedback)) {
            if(employeeOrderQueries.subtractFeedbackCount(feedback)) {
              return "Feedback added successfully.";
            } else {
              System.out.println("Unable to subtract feedback count.");
              return "Feedback Successful";
            }
          } else {
            return "Unable to add feedback.";
          }
        } else {
          return "Invalid menu item or employee ID.";
        }
      } else {
        return "You have not voted for this item. You can give feedback once you vote for the item.";
      }
    } else {
      return "Invalid menu item or employee ID.";
    }
  }
}
