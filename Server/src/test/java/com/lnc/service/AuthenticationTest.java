package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.utils.ConversionFromJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationTest {

    private Authentication authenticationUnderTest;
    private ConversionFromJson fromJsonConverterMock;

    @BeforeEach
    void setUp() {
        authenticationUnderTest = new Authentication();
        fromJsonConverterMock = mock(ConversionFromJson.class);
    }

    @Test
    void testAuthenticate() {
        String jsonData = "{\"employeeID\":\"WRN001\",\"password\":\"wrong_password\"}";

        final String result = authenticationUnderTest.authenticate(jsonData);

        assertEquals("EmployeeID does not exist.", result);
    }

    @Test
    void testAuthenticateUser() throws Exception {
        String employeeID = "EMP001";
        String password = "wrong_password";
        final String result = authenticationUnderTest.authenticateUser(employeeID, password);

        assertEquals("Wrong username or password.", result);
    }

    @Test
    void testAuthenticateUser_ReturnsErrorMessageOnNullPointerException() throws JsonProcessingException {
        String jsonData = "{\"empID\":\"wrong_template\"}";
        when(fromJsonConverterMock.getJsonValue(anyString(), eq(jsonData))).thenThrow(NullPointerException.class);

        String result = authenticationUnderTest.authenticate(jsonData);

        assertEquals("Wrong request format.", result);
    }
}
