package service.employee;

import com.lnc.service.employee.MenuVote;
import org.junit.jupiter.api.Test;

public class TestMenuVote {
    @Test
    public void testInvalidJsonFormat() {
        String jsonData = "{\"votedItems\":[\"Misoshiru\",\"Teriyaki Chicken\",\"Gyoza\",\"Stir-Fried Noodles\"],\"employee_ID\":\"EMP001\"}";

        MenuVote menuVote = new MenuVote();
        String response = menuVote.voteForMenu(jsonData);

        assert response.equals("Error while processing your vote.");
    }

    @Test
    public void testNullEmployeeID() {
        String jsonData = "{\"votedItems\":[\"Misoshiru\",\"Teriyaki Chicken\",\"Gyoza\",\"Stir-Fried Noodles\"]}";

        MenuVote menuVote = new MenuVote();
        String response = menuVote.voteForMenu(jsonData);

        assert response.equals("Error while processing your vote.");
    }

    @Test
    public void testInvalidItem() {
        String jsonData = "{\"votedItems\":[\"Imaginary Item\",\"Teriyaki Chicken\",\"Gyoza\",\"Stir-Fried Noodles\"],\"employeeID\":\"EMP001\"}";

        MenuVote menuVote = new MenuVote();
        String response = menuVote.voteForMenu(jsonData);

        assert response.equals("Invalid item. Imaginary Item not found in the menu.");
    }
}
