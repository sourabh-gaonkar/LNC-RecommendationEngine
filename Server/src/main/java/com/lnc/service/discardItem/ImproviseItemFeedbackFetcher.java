package com.lnc.service.discardItem;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.ImproviseFeedbackAnswerQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ImproviseItemFeedbackFetcher {
    private final Logger logger = Logger.getLogger(ImproviseItemFeedbackFetcher.class.getName());
    private final ConversionFromJson conversionFromJson = new ConversionFromJson();
    private final MenuQueries menuQueries = new MenuQueries();
    private final ImproviseFeedbackAnswerQueries improviseFeedbackAnswerQueries = new ImproviseFeedbackAnswerQueries();
    private final ConversionToJson conversionToJson = new ConversionToJson();
    public String fetchFeedbacks(String jsonData) {
        try {
            String itemName = conversionFromJson.getJsonValue("itemName", jsonData);
            if (itemName == null) {
                return "Item name not found in request.";
            }
            if(!menuQueries.checkMenuItemPresent(itemName)) {
                return "Item not found in menu.";
            }
            int itemId = menuQueries.getItemID(itemName);
            List<Map<String, List<String>>> questionAnswers = improviseFeedbackAnswerQueries.getAnswersWithQuestion(itemId);
            if (questionAnswers == null || questionAnswers.isEmpty()) {
                return "No feedback available for item.";
            }
            return conversionToJson.codeQuestionAnswers(questionAnswers);

        } catch (JsonProcessingException | NullPointerException ex) {
            logger.severe("Failed to fetch feedback for item.\n" + ex.getMessage());
            return "Failed to fetch feedback for item.";
        }
    }
}
