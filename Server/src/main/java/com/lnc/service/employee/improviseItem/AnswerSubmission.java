package com.lnc.service.employee.improviseItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.ImproviseFeedbackAnswerQueries;
import com.lnc.utils.ConversionFromJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AnswerSubmission {
    private final Logger logger = Logger.getLogger(AnswerSubmission.class.getName());
    private final ConversionFromJson conversionFromJson = new ConversionFromJson();
    private final ImproviseFeedbackAnswerQueries improviseFeedbackAnswerQueries = new ImproviseFeedbackAnswerQueries();
    public String submitAnswer(String jsonData){
        try{
            System.out.println("Inside submitAnswer method.");
            Map<String, Object> parsedJsonData = conversionFromJson.decodeAnswers(jsonData);
            String employeeId = (String) parsedJsonData.get("employeeId");
            List<Map<String, String>> answers = (List<Map<String, String>>) parsedJsonData.get("answers");
            if(employeeId == null || answers == null || answers.isEmpty()){
                return "Server error while submitting answer.";
            }
            System.out.println("Before adding answer.");
            for(Map<String, String> answer : answers){
                System.out.println("Adding answer." + answer.toString());
                boolean isAnswerAdded = improviseFeedbackAnswerQueries.addAnswer(employeeId, answer);
                if(!isAnswerAdded){
                    return "Second submission not allowed.";
                }
            }
            return "Answer submitted successfully.";
        } catch (JsonProcessingException | NullPointerException e){
            logger.severe("Error in converting discarded item to JSON" + e.getMessage());
            return "Failed to submit answer.";
        }
    }
}
