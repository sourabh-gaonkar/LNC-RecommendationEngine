package service.employee;

import com.lnc.service.employee.EmployeeFeedback;
import org.junit.jupiter.api.Test;

public class TestEmployeeFeedback {
    @Test
    public void testInvalidJsonData() {
        String jsonData = "{\"employee_ID\":\"EMP001\",\"menuItem\":\"Pulao\",\"rating\":4,\"comment\":\"gg\"}";

        EmployeeFeedback employeeFeedback = new EmployeeFeedback();
        String result = employeeFeedback.getEmployeeFeedback(jsonData);

        assert(result.equals("Invalid JSON format."));
    }

    @Test
    public void testInvalidMenuItem() {
        String jsonData = "{\"employeeID\":\"EMP001\",\"menuItem\":\"Imaginary Item\",\"rating\":4,\"comment\":\"gg\"}";

        EmployeeFeedback employeeFeedback = new EmployeeFeedback();
        String result = employeeFeedback.getEmployeeFeedback(jsonData);

        assert(result.equals("Invalid menu item or employee ID."));
    }

    @Test
    public void testInvalidEmployeeID() {
        String jsonData = "{\"employeeID\":\"EMC001\",\"menuItem\":\"Pulao\",\"rating\":4,\"comment\":\"gg\"}";

        EmployeeFeedback employeeFeedback = new EmployeeFeedback();
        String result = employeeFeedback.getEmployeeFeedback(jsonData);

        assert(result.equals("Invalid menu item or employee ID."));
    }

    @Test
    public void testFeedbackNotGiven() {
        String jsonData = "{\"employeeID\":\"EMP001\",\"menuItem\":\"Pulao\",\"rating\":4,\"comment\":\"gg\"}";

        EmployeeFeedback employeeFeedback = new EmployeeFeedback();
        String result = employeeFeedback.getEmployeeFeedback(jsonData);

        assert(result.equals("You have not voted for this item. You can give feedback once you vote for the item."));
    }
}
