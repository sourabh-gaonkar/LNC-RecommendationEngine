package com.lnc.service.employee;

import com.lnc.connection.ServerConnection;
import com.lnc.model.Feedback;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;

public class EmployeeFeedback {
    public void takeEmployeeFeedback(String employeeID) throws IOException {
        Feedback feedback = new Feedback();
        feedback.setEmployeeID(employeeID);
        feedback.setMenuItem(getMenuItem());
        feedback.setRating(getRating());
        feedback.setComment(getComment());

        ToJsonConversion toJsonConversion = new ToJsonConversion();
        String request = toJsonConversion.codeEmployeeFeedback(feedback);

        try{
            String response = ServerConnection.requestServer(request);
            System.out.println("response: " + response);
        } catch (Exception ex){
            System.out.println("Server connection error.");
        }
    }

    private String getMenuItem() throws IOException {
        String menuItem;
        while(true){
            menuItem = InputHandler.getString("\nEnter menu item: ");
            if(menuItem.isEmpty()){
                System.out.println("Comment cannot be empty.");
                continue;
            }
            break;
        }
        return menuItem;
    }

    private int getRating() throws IOException {
        int rating;
        while(true){
            rating = InputHandler.getInt("Enter rating: ");
            if (!(rating >= 1 && rating <=5)) {
                System.out.println("Enter valid choice(1-5).\n");
                continue;
            }
            break;
        }
        return rating;
    }

    private String getComment() throws IOException {
        String comment;
        while(true){
            comment = InputHandler.getString("Enter review comment: ");
            if(comment.isEmpty()){
                System.out.println("Comment cannot be empty.\n");
                continue;
            }
            break;
        }
        return comment;
    }
}
