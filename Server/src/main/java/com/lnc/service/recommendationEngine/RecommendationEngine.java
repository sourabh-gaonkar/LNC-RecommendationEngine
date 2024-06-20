package com.lnc.service.recommendationEngine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.RecommendationEngineQueries;
import com.lnc.utils.ConversionToJson;
import java.util.*;
import java.util.logging.Logger;

public class RecommendationEngine {
    private final Logger logger = Logger.getLogger(RecommendationEngine.class.getName());

    public String runEngine() {
        try {
            RecommendationEngineQueries engineData = new RecommendationEngineQueries();
            Map<String, List<Map<String, Object>>> dataFrames = engineData.getAllData();

            DataProcessor processor = new DataProcessor();
            List<Map<String, Object>> recommendations = processor.processDataFrames(dataFrames);

            ConversionToJson toJson = new ConversionToJson();
            return toJson.codeEngineResponse(recommendations);
        } catch (JsonProcessingException | NullPointerException e) {
            logger.severe("Error in Recommendation Engine: " + e.getMessage());
            return "Error in Recommendation Engine.";
        }
    }
}
