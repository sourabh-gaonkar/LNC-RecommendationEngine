package com.lnc.service.discardItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.DiscardMenuQueries;
import com.lnc.DB.ImproviseFeedbackQuestionQueries;
import com.lnc.DB.ImproviseFeedbackSessionQueries;
import com.lnc.DB.ImproviseMenuQueries;
import com.lnc.utils.ConversionFromJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ImproviseQuestions {
    private final Logger logger = Logger.getLogger(ImproviseQuestions.class.getName());
    private final ImproviseMenuQueries improviseMenuQueries = new ImproviseMenuQueries();
    private final DiscardMenuQueries discardMenuQueries = new DiscardMenuQueries();
    private final ConversionFromJson conversionFromJson = new ConversionFromJson();
    private final ImproviseFeedbackSessionQueries improviseFeedbackSessionQueries = new ImproviseFeedbackSessionQueries();
    private final ImproviseFeedbackQuestionQueries improviseFeedbackQuestionQueries = new ImproviseFeedbackQuestionQueries();

    public String addQuestions(String jsonData) {
        try{
            Map<String, Object> parsedJsonData = conversionFromJson.decodeQuestions(jsonData);
            String itemName = (String) parsedJsonData.get("itemName");
            List<String> questions = (List<String>) parsedJsonData.get("questions");
            if(!initializeDatabase(itemName)){
                return "Failed to add questions for the improvised item.";
            }
            int feedbackSessionID = createItemFeedbackSession(itemName);
            if (feedbackSessionID == 0) {
                return "Failed to create feedback session for the improvised item.";
            }
            if(!addQuestionsToFeedbackSession(feedbackSessionID, questions)){
                return "Failed to add questions for the improvised item.";
            }
            return "Questions added to the discarded item.";
        } catch (JsonProcessingException | NullPointerException e){
            logger.severe("Error in converting discarded item to JSON" + e.getMessage());
            return "Failed to add questions for the improvised item.";
        }
    }

    private boolean initializeDatabase(String itemName) {
        if (improviseMenuQueries.addToImproviseMenu(itemName)) {
            if(discardMenuQueries.removeFromDiscardMenu(itemName)){
                return true;
            } else {
                logger.severe("Failed to remove item from discard menu");
            }
        } else {
            logger.severe("Failed to add item to improvise menu");
        }
        return false;
    }

    private int createItemFeedbackSession(String itemName) {
        return improviseFeedbackSessionQueries.createFeedbackSession(itemName);
    }

    private boolean addQuestionsToFeedbackSession(int feedbackSessionID, List<String> questions) {
        return improviseFeedbackQuestionQueries.addQuestions(feedbackSessionID, questions);
    }
}
