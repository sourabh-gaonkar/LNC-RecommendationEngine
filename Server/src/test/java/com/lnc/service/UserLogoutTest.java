package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.utils.ConversionFromJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserLogoutTest {

    private UserLogout userLogoutUnderTest;
    private ConversionFromJson fromJsonConverterMock;

    @BeforeEach
    void setUp() {
        userLogoutUnderTest = new UserLogout();
        fromJsonConverterMock = mock(ConversionFromJson.class);
    }

    @Test
    void testLogout() throws JsonProcessingException {
        String jsonData = "{\"empID\":\"wrong_template\"}";
        when(fromJsonConverterMock.getJsonValue(anyString(), eq(jsonData))).thenThrow(NullPointerException.class);

        String result = userLogoutUnderTest.logout(jsonData);

        assertEquals("Failed to logout user.", result);
    }
}
