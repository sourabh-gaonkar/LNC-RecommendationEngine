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

  public String generateReport(String jsonData) {
    try {
      ConversionFromJson fromJson = new ConversionFromJson();
      String year = fromJson.getJsonValue("year", jsonData);
      String month = fromJson.getJsonValue("month", jsonData);

      FeedbackQueries feedbackQueries = new FeedbackQueries();
      List<Map<String, Object>> reportData = feedbackQueries.generateFeedbackReport(month, year);

      if (reportData == null) {
        return "Error in generating report";
      }

      ConversionToJson toJson = new ConversionToJson();
      return toJson.codeMonthlyReport(reportData);

    } catch (JsonProcessingException ex) {
      LOGGER.log(Level.SEVERE, "JSON processing error in generating report: " + ex.getMessage(), ex);
      return "Error in generating report";
    } catch (NullPointerException ex) {
      LOGGER.log(Level.SEVERE, "Null value encountered in generating report: " + ex.getMessage(), ex);
      return "Error in generating report";
    }
  }
}
