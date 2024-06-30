package com.lnc.service.employee.improviseItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.util.FromJsonConversion;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ImproviseFeedback {
    private final Logger logger = Logger.getLogger(ImproviseFeedback.class.getName());
    private final ToJsonConversion toJsonConversion = new ToJsonConversion();
    private final FromJsonConversion fromJsonConversion = new FromJsonConversion();
    public void giveImprovisationFeedback(String improvisationItem, String employeeId) {
        try {
            List<Map<Integer, String>> questions = getQuestions(improvisationItem);
            if (questions == null || questions.isEmpty()) {
                System.out.println("No questions available for " + improvisationItem);
                return;
            }

            List<Map<Integer, String>> answers = getEmployeeAnswers(questions);
            String request = toJsonConversion.codeAnswers(answers, employeeId);
            String response = ServerConnection.requestServer(request);
            System.out.println(response);
        } catch (JsonProcessingException e) {
            logger.severe("Failed to get questions for " + improvisationItem + ".\n" + e.getMessage());
        } catch (IOException e) {
            logger.severe("Error while getting user input.\n" + e.getMessage());
        }
    }

    private List<Map<Integer, String>> getQuestions(String itemName) throws JsonProcessingException {
        String apiPath = "/discardItem/getQuestions";
        String request = toJsonConversion.codeItemName(itemName, apiPath);
        String response = ServerConnection.requestServer(request);
        return fromJsonConversion.decodeQuestions(response);
    }

    private List<Map<Integer, String>> getEmployeeAnswers(List<Map<Integer, String>> questions) throws IOException {
        List<Map<Integer, String>> answers = new ArrayList<>();
        for (Map<Integer, String> questionMap : questions) {
            for (Map.Entry<Integer, String> entry : questionMap.entrySet()) {
                System.out.println("\n" + entry.getValue());
                String answer = readAnswer();
                Map<Integer, String> answerMap = new HashMap<>();
                answerMap.put(entry.getKey(), answer);
                answers.add(answerMap);
            }
        }
        return answers;
    }

    private String readAnswer() throws IOException {
        while(true) {
            String answer = InputHandler.getString("Enter your answer: ");
            if (answer.isEmpty()) {
                System.out.println("Answer cannot be empty. Please try again.\n");
            } else {
                return answer;
            }
        }
    }
}
