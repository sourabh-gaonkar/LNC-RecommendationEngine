package com.lnc.service.chef;

import com.lnc.DB.FeedbackQueries;
import com.lnc.utils.FromJson;
import com.lnc.utils.ToJson;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
  public String generateReport(String jsonData) throws Exception {
    FromJson fromJson = new FromJson();
    String year = fromJson.getJsonValue("year", jsonData);
    String month = fromJson.getJsonValue("month", jsonData);

    FeedbackQueries feedbackQueries = new FeedbackQueries();
    List<Map<String, Object>> reportData = feedbackQueries.generateFeedbackReport(month, year);

    ToJson toJson = new ToJson();
    return toJson.codeMonthlyReport(reportData);
  }
}
