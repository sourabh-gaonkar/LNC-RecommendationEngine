package com.lnc.service.employee;

import com.lnc.DB.MenuRolloutQueries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TomorrowsMenuTest {

    private TomorrowsMenu tomorrowsMenuUnderTest;
    private MenuRolloutQueries menuRolloutQueries;

    @BeforeEach
    void setUp() {
        tomorrowsMenuUnderTest = new TomorrowsMenu();
        menuRolloutQueries = Mockito.mock(MenuRolloutQueries.class);
    }

    @Test
    void testGetTomorrowsMenuEmptyMenu() {
        List<Map<String, Object>> emptyList = new ArrayList<>();
        when(menuRolloutQueries.getTomorrowsMenu()).thenReturn(emptyList);

        String expectedResult = "[]";

        String actualResult = tomorrowsMenuUnderTest.getTomorrowsMenu("{\"employee_id\": 1}");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetTomorrowsMenuInvalidTemplate() {
        String jsonData = "{\"key\":\"value\"}";

        String result = tomorrowsMenuUnderTest.getTomorrowsMenu(jsonData);

        assertNull(result);
    }
}
