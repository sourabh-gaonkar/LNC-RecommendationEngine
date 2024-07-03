package com.lnc.service.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuVoteTest {

    private MenuVote menuVoteUnderTest;

    @BeforeEach
    void setUp() {
        menuVoteUnderTest = new MenuVote();
    }

    @Test
    void testVoteForMenuInvalidTemplate() {
        String jsonData = "{\"key\":\"invalid template\"}";

        String response = menuVoteUnderTest.voteForMenu(jsonData);

        assertEquals("Error while processing your vote.", response);
    }

    @Test
    void testVoteForMenuInvalidMenuItem() {
        String jsonData = "{\"votedItems\":[\"Invalid Menu Item\",\"Garlic Noodles\",\"Rasgulla\",\"Tofu Teriyaki\"],\"employeeID\":\"EMP001\"}";

        String response = menuVoteUnderTest.voteForMenu(jsonData);

        assertEquals("Invalid item. Invalid Menu Item not found in the menu.", response);
    }
}
