package com.lnc.service.chef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportGeneratorTest {

    private ReportGenerator reportGeneratorUnderTest;

    @BeforeEach
    void setUp() {
        reportGeneratorUnderTest = new ReportGenerator();
    }

    @Test
    void testGenerateReportInvalidDate() {
        String jsonData  = "{\"year\":\"9999\",\"month\":\"12\"}";

        String result = reportGeneratorUnderTest.generateReport(jsonData);

        assertEquals("[]", result);
    }

    @Test
    void testGenerateReportInvalidTemplate() {
        String jsonData  = "{\"year_invalid\":\"9999\",\"month_invalid\":\"12\"}";

        String result = reportGeneratorUnderTest.generateReport(jsonData);

        assertEquals("Error in generating report", result);
    }
}
