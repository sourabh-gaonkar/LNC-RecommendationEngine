package com.lnc.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DAO.FeedbackQueries;
import com.lnc.DAO.Menu;
import com.lnc.DAO.UserDetails;
import com.lnc.json.FromJson;

import java.sql.SQLException;

public class EmployeeFeedback {
    public String getEmployeeFeedback(String jsonData) throws Exception {
        FromJson fromJson = new FromJson();
        Feedback feedback = fromJson.decodeFeedback(jsonData);

        Menu menu = new Menu();
        UserDetails userDetails = new UserDetails();
        FeedbackQueries feedbackQueries = new FeedbackQueries();
        if(menu.checkMenuItemPresent(feedback.getMenuItem()) && userDetails.validateEmployeeID(feedback.getEmployeeID())){
            if(feedbackQueries.addFeedback(feedback)){
                return "Feedback added successfully.";
            } else {
                return "Unable to add feedback.";
            }
        } else {
            return "Invalid menu item or employee ID.";
        }
    }
}
