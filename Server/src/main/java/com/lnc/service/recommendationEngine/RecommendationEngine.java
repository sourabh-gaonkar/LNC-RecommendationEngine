package com.lnc.service.recommendationEngine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.RecommendationEngineQueries;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RecommendationEngine {
    private final Logger logger = Logger.getLogger(RecommendationEngine.class.getName());
    private final ConversionFromJson fromJsonConverter = new ConversionFromJson();
    private final RecommendationEngineQueries engineData = new RecommendationEngineQueries();
    private final DataProcessor processor = new DataProcessor();
    private final ConversionToJson toJson = new ConversionToJson();

    public String runEngine(String jsonData) {
        try {
            int itemCount = Integer.parseInt(fromJsonConverter.getJsonValue("itemCount", jsonData));

            Map<String, List<Map<String, Object>>> dataFrames = engineData.getAllData();

            List<Map<String, Object>> recommendations = processor.processDataFrames(dataFrames, itemCount);

            return toJson.codeEngineResponse(recommendations);
        } catch (JsonProcessingException | NullPointerException e) {
            logger.severe("Error in Recommendation Engine: " + e.getMessage());
            return "Error in Recommendation Engine.";
        }
    }
}
