package com.lnc.service.employee.improviseItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.ImproviseFeedbackQuestionQueries;
import com.lnc.DB.ImproviseFeedbackSessionQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ImproviseItemQuestions {
    private final Logger logger = Logger.getLogger(ImproviseItemQuestions.class.getName());
    private final ConversionFromJson conversionFromJson = new ConversionFromJson();
    private final MenuQueries menuQueries = new MenuQueries();
    private final ImproviseFeedbackSessionQueries improviseFeedbackSessionQueries = new ImproviseFeedbackSessionQueries();
    private final ImproviseFeedbackQuestionQueries improviseFeedbackQuestionQueries = new ImproviseFeedbackQuestionQueries();
    private final ConversionToJson conversionToJson = new ConversionToJson();
    public String getQuestions(String jsonData){
        try {
            String itemName = conversionFromJson.getJsonValue("itemName", jsonData);
            int itemID = menuQueries.getItemID(itemName);
            int sessionID = improviseFeedbackSessionQueries.getFeedbackSessionID(itemID);
            List<Map<Integer, String>> questions = improviseFeedbackQuestionQueries.getQuestions(sessionID);
            return conversionToJson.codeQuestions(questions);
        } catch (JsonProcessingException | NullPointerException e) {
            logger.severe("Failed to get questions for " + jsonData + ".\n" + e.getMessage());
            return "Failed to get questions.";
        }
    }
}
