package com.lnc.service.employee;

import com.lnc.DB.FeedbackQueries;
import com.lnc.DB.Menu;
import com.lnc.DB.UserDetails;
import com.lnc.model.Feedback;
import com.lnc.utils.FromJson;

public class EmployeeFeedback {
  public String getEmployeeFeedback(String jsonData) throws Exception {
    FromJson fromJson = new FromJson();
    Feedback feedback = fromJson.decodeFeedback(jsonData);

    Menu menu = new Menu();
    UserDetails userDetails = new UserDetails();
    FeedbackQueries feedbackQueries = new FeedbackQueries();
    if (menu.checkMenuItemPresent(feedback.getMenuItem())
        && userDetails.validateEmployeeID(feedback.getEmployeeID())) {
      if (feedbackQueries.addFeedback(feedback)) {
        return "Feedback added successfully.";
      } else {
        return "Unable to add feedback.";
      }
    } else {
      return "Invalid menu item or employee ID.";
    }
  }
}
