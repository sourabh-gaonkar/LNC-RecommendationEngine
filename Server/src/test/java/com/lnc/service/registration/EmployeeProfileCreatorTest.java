package com.lnc.service.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeProfileCreatorTest {

    private EmployeeProfileCreator employeeProfileCreatorUnderTest;

    @BeforeEach
    void setUp() {
        employeeProfileCreatorUnderTest = new EmployeeProfileCreator();
    }

    @Test
    void testCreateEmployeeProfile() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String response = employeeProfileCreatorUnderTest.createEmployeeProfile(jsonData);

        assertEquals("Error processing employee profile creation.", response);
    }
}
