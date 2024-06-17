package com.lnc.service.chef;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.connection.ServerConnection;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ReportGenerator {
  public void generateReport() throws Exception {
        String month;
        String year;
        while(true) {
            month = getMonth();
            year = getYear();
            if (validateMonthYear(month, year)) {
                break;
            } else {
                System.out.println("Invalid month/year. Please enter a valid month/year.");
            }
        }
        System.out.println("Generating report for " + month + "/" + year + "...");

        ToJsonConversion convertToJson = new ToJsonConversion();
        String request = convertToJson.codeMonthYear(month, year);

        String response = ServerConnection.requestServer(request);

        writeReportToFile(response, year, month);
    }

    private String getMonth() throws IOException {
        int month;
        while (true) {
            month = InputHandler.getInt("\nEnter month (1-12): ");
            if (month >= 1 && month <= 12) {
                return Integer.toString(month);
            } else {
                System.out.println("Invalid month. Please enter a valid month.");
            }
        }
    }

    private String getYear() throws IOException {
        int year;
        while (true) {
            year = InputHandler.getInt("Enter year: ");
            if (year >= 1900 && year <= 9999) {
                return Integer.toString(year);
            } else {
                System.out.println("Invalid year. Please enter a valid year.\n");
            }
        }
    }

    private boolean validateMonthYear(String month, String year) {
        int currentYear = java.time.Year.now().getValue();
        int currentMonth = java.time.MonthDay.now().getMonthValue();
        int inputYear = Integer.parseInt(year);
        int inputMonth = Integer.parseInt(month);

        if (inputYear < currentYear) {
            return true;
        } else return inputYear == currentYear && inputMonth <= currentMonth;
    }

  public void writeReportToFile(String jsonResult, String year, String month) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String reportFileName = "feedback_report_" + year + "_" + month + ".txt";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        JsonNode rootNode = objectMapper.readTree(jsonResult);
        BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName));

        for (JsonNode itemNode : rootNode) {
            String itemName = itemNode.path("item_name").asText();
            double avgRating = itemNode.path("average_rating").asDouble();

            writer.write("Feedback for item: " + itemName + "\n");
            writer.write("====================\n");

            for (JsonNode feedbackNode : itemNode.path("feedbacks")) {
                String employeeName = feedbackNode.path("employee_name").asText();
                int rating = feedbackNode.path("rating").asInt();
                String comment = feedbackNode.path("comment").asText();
                long dateMillis = feedbackNode.path("date").asLong();
                String date = dateFormat.format(new Date(dateMillis));

                writer.write("Employee: " + employeeName + "\n");
                writer.write("Rating: " + rating + "\n");
                writer.write("Comment: " + comment + "\n");
                writer.write("Date: " + date + "\n");
                writer.write("------------------------\n");
            }

            writer.write("Average rating for " + itemName + ": " + avgRating + "\n\n\n\n\n");
        }

        writer.close();
        System.out.println("Report generated successfully: " + reportFileName);
    }
}
