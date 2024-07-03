package com.lnc.service.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllNotificationsOfEmployeeTest {

    private AllNotificationsOfEmployee allNotificationsOfEmployeeUnderTest;

    @BeforeEach
    void setUp() {
        allNotificationsOfEmployeeUnderTest = new AllNotificationsOfEmployee();
    }

    @Test
    void testGetAllNotificationsOfEmployee() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String response = allNotificationsOfEmployeeUnderTest.getAllNotificationsOfEmployee(jsonData);

        assertEquals("Error in fetching notifications", response);
    }
}
