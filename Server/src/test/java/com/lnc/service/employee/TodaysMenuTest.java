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

class TodaysMenuTest {

    private TodaysMenu todaysMenuUnderTest;
    private MenuRolloutQueries menuRolloutQueries;

    @BeforeEach
    void setUp() {
        todaysMenuUnderTest = new TodaysMenu();
        menuRolloutQueries = Mockito.mock(MenuRolloutQueries.class);
    }

    @Test
    void testGetTodaysMenuInvalidTemplate() {
        String jsonData = "{\"key\":\"value\"}";

        String result = todaysMenuUnderTest.getTodaysMenu(jsonData);

        assertNull(result);
    }

    @Test
    void testGetTodaysMenuEmptyMenu() {
        List<Map<String, Object>> emptyList = new ArrayList<>();
        when(menuRolloutQueries.getTodaysMenu()).thenReturn(emptyList);

        String actualResult = todaysMenuUnderTest.getTodaysMenu("{\"employee_id\": 1}");

        assertNull(actualResult);
    }
}
