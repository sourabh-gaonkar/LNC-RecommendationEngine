package com.lnc.controller;

import com.lnc.service.chef.FeedbackDisplay;
import com.lnc.service.chef.ReportGenerator;
import com.lnc.service.chef.RolloutMenu;
import com.lnc.service.recommendationEngine.RecommendationEngine;

public class ChefRoutes implements RouteHandler {
    private final FeedbackDisplay feedbackDisplay = new FeedbackDisplay();
    private final RecommendationEngine recommendationEngine = new RecommendationEngine();
    private final RolloutMenu rolloutMenu = new RolloutMenu();
    private final ReportGenerator reportGenerator = new ReportGenerator();

    @Override
    public String handle(String path, String data) throws Exception {
        return switch (path) {
            case "/chef/getFeedback" -> feedbackDisplay.displayFeedback(data);
            case "/chef/getRecommendation" -> recommendationEngine.runEngine(data);
            case "/chef/rolloutMenu" -> rolloutMenu.rolloutMenu(data);
            case "/chef/generateReport" -> reportGenerator.generateReport(data);
            default -> throw new IllegalArgumentException("Invalid path for ChefRoutes: " + path);
        };
    }
}
