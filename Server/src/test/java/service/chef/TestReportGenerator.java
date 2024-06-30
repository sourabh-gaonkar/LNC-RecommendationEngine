package service.chef;

import com.lnc.service.chef.ReportGenerator;
import org.junit.jupiter.api.Test;

public class TestReportGenerator {
    @Test
    public void testInvalidDate() {
        String jsonData = "{\"month\":\"12\",\"year\":\"2024\"}";
        String emptyReport = "[]";

        ReportGenerator reportGenerator = new ReportGenerator();
        String report = reportGenerator.generateReport(jsonData);

        assert(report.equals(emptyReport));
    }

    @Test
    public void testNullDate() {
        String jsonData = "{\"month\":\"12\"}";

        ReportGenerator reportGenerator = new ReportGenerator();
        String report = reportGenerator.generateReport(jsonData);

        assert(report.equals("Error in generating report"));
    }

    @Test
    public void testInvalidJsonData() {
        String jsonData = "{\"month_of_the_year\":\"12\",\"year\":\"2024\"}";

        ReportGenerator reportGenerator = new ReportGenerator();
        String report = reportGenerator.generateReport(jsonData);

        assert(report.equals("Error in generating report"));
    }
}
