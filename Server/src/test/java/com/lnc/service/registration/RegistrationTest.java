package com.lnc.service.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationTest {

    private Registration registrationUnderTest;

    @BeforeEach
    void setUp() {
        registrationUnderTest = new Registration();
    }

    @Test
    void testAddUser() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String result = registrationUnderTest.addUser(jsonData);

        assertEquals("Invalid data format.", result);
    }
}
