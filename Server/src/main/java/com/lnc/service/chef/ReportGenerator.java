package com.lnc.service.chef;

import com.lnc.DB.FeedbackQueries;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
  public String generateReport(String jsonData) throws Exception {
    ConversionFromJson fromJson = new ConversionFromJson();
    String year = fromJson.getJsonValue("year", jsonData);
    String month = fromJson.getJsonValue("month", jsonData);

    FeedbackQueries feedbackQueries = new FeedbackQueries();
    List<Map<String, Object>> reportData = feedbackQueries.generateFeedbackReport(month, year);

    ConversionToJson toJson = new ConversionToJson();
    return toJson.codeMonthlyReport(reportData);
  }
}
