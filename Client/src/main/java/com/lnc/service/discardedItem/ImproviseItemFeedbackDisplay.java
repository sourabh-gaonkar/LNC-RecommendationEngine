package com.lnc.service.discardedItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.service.employee.improviseItem.ImproviseFeedback;
import com.lnc.util.FromJsonConversion;
import com.lnc.util.InputHandler;
import com.lnc.util.JsonDataFormat;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ImproviseItemFeedbackDisplay {
    private final Logger logger = Logger.getLogger(ImproviseFeedback.class.getName());
    private final ToJsonConversion toJsonConversion = new ToJsonConversion();
    private final JsonDataFormat jsonDataFormat = new JsonDataFormat();

    public void displayFeedback() {
        try{
            List<String> improviseItemList = getImproviseItemList();
            if(improviseItemList == null || improviseItemList.isEmpty()){
                System.out.println("No items to improvise.");
                return;
            }

            System.out.println("\nSelect item to give Feedback.");
            int itemChoice = getValidChoice(improviseItemList.size());
            String improviseItem = improviseItemList.get(itemChoice - 1);

            String request = toJsonConversion.codeItemName(improviseItem, "/discardItem/getFeedback");
            String response = ServerConnection.requestServer(request);

            System.out.println("\n---------------------------------------------------");
            System.out.println("Feedback for " + improviseItem + " is:\n");
            jsonDataFormat.printImprovisedItemFeedbacks(response);
            System.out.println("---------------------------------------------------");


        } catch (JsonProcessingException ex){
            logger.severe("Failed to get improvise items.\n" + ex.getMessage());
        } catch (IOException e) {
            logger.severe(("Error while getting user input.\n" + e.getMessage()));
        }
    }

    private List<String> getImproviseItemList() throws JsonProcessingException {
        String apiPath = "/discardItem/viewImproviseItem";
        String request = apiPath + "& ";
        String response = ServerConnection.requestServer(request);
        return jsonDataFormat.printImprovisedItems(response);
    }

    private int getValidChoice(int maxChoice) throws IOException {
        int choice;
        do {
            choice = InputHandler.getInt("Enter your choice: ");
            if (choice < 1 || choice > maxChoice) {
                System.out.println("Invalid choice. Please try again.\n");
            }
        } while (choice < 1 || choice > maxChoice);
        return choice;
    }
}
