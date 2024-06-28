package com.lnc.service.discardedItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DiscardedItemImprovisation {
    private final Logger logger = Logger.getLogger(DiscardedItemImprovisation.class.getName());
    private final ToJsonConversion toJsonConversion = new ToJsonConversion();
    public void improviseDiscardedItem(String discardedItem) {
        try{
            int numberOfQuestions = getNoOfQuestions();
            List<String> questions = getQuestions(numberOfQuestions);

            String request = toJsonConversion.codeQuestions(questions, discardedItem);

            String response = ServerConnection.requestServer(request);
            System.out.println(response);
        } catch (JsonProcessingException | NullPointerException ex){
            logger.severe("Error in converting discarded item to JSON" + ex.getMessage());
        } catch (IOException ex) {
            logger.severe("Error in getting number of questions" + ex.getMessage());
        }
    }

    private int getNoOfQuestions() throws IOException {
        int numberOfQuestions;
        while(true){
            numberOfQuestions = InputHandler.getInt("\nEnter the number of questions(min-3, max-5): ");
            if(numberOfQuestions < 3 || numberOfQuestions > 5){
                System.out.println("Invalid number of questions. Please enter a number between 3 and 5.");
                continue;
            }
            return numberOfQuestions;
        }
    }

    private List<String> getQuestions(int numberOfQuestions) throws IOException {
        List<String> questions = new ArrayList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            String question = InputHandler.getString("Enter question " + (i + 1) + ": ");
            if(question.isEmpty()){
                System.out.println("Question cannot be empty. Please enter a valid question.");
                i--;
                continue;
            }
            questions.add(question);
        }
        return questions;
    }
}
