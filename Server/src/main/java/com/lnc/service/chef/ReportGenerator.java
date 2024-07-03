package com.lnc.service.chef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.FeedbackQueries;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportGenerator {

  private static final Logger LOGGER = Logger.getLogger(ReportGenerator.class.getName());
  private final ConversionFromJson fromJson = new ConversionFromJson();
  private final FeedbackQueries feedbackQueries = new FeedbackQueries();
  private final ConversionToJson toJson = new ConversionToJson();

  public String generateReport(String jsonData) {
    try {
      String year = fromJson.getJsonValue("year", jsonData);
      String month = fromJson.getJsonValue("month", jsonData);

      List<Map<String, Object>> reportData = feedbackQueries.generateFeedbackReport(month, year);

      if (reportData == null) {
        return "Error in generating report.";
      }

      return toJson.codeMonthlyReport(reportData);

    } catch (JsonProcessingException | NullPointerException ex) {
      LOGGER.log(Level.SEVERE, "JSON processing error in generating report: " + ex.getMessage(), ex);
      return "Error in generating report";
    }
  }
}
