package com.lnc.service.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeFeedbackTest {

    private EmployeeFeedback employeeFeedbackUnderTest;

    @BeforeEach
    void setUp() {
        employeeFeedbackUnderTest = new EmployeeFeedback();
    }

    @Test
    void testGetEmployeeFeedbackInvalidItemId() {
        String jsonData = "{\"employeeID\":\"EMP001\",\"menuItem\":\"invalid item\",\"rating\":5,\"comment\":\"mangoes were fresh and it tasted nice.\"}";
        String expectedResult = "Invalid menu item or employee ID.";

        String actualResult = employeeFeedbackUnderTest.getEmployeeFeedback(jsonData);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetEmployeeFeedbackInvalidEmployeeId() {
        String jsonData = "{\"employeeID\":\"Invalid id\",\"menuItem\":\"Mango lassi\",\"rating\":5,\"comment\":\"mangoes were fresh and it tasted nice.\"}";
        String expectedResult = "Invalid menu item or employee ID.";

        String actualResult = employeeFeedbackUnderTest.getEmployeeFeedback(jsonData);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetEmployeeFeedbackInvalidTemplate() {
        String jsonData = "{\"key\":\"invalid template\"}";
        String expectedResult = "Invalid JSON format.";

        String actualResult = employeeFeedbackUnderTest.getEmployeeFeedback(jsonData);

        assertEquals(expectedResult, actualResult);
    }
}
