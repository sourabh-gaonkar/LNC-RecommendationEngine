package com.lnc.service.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeProfileEditorTest {

    private EmployeeProfileEditor employeeProfileEditorUnderTest;

    @BeforeEach
    void setUp() {
        employeeProfileEditorUnderTest = new EmployeeProfileEditor();
    }

    @Test
    void testEditEmployeeProfile() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String response = employeeProfileEditorUnderTest.editEmployeeProfile(jsonData);

        assertEquals("Error processing employee profile update.", response);
    }
}
