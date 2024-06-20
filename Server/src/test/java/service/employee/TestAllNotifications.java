package service.employee;

import com.lnc.service.employee.AllNotificationsOfEmployee;
import org.junit.jupiter.api.Test;

public class TestAllNotifications {
    @Test
    public void testInvalidJsonData() {
        String jsonData = "{\"employeeId\":\"EMP008\"}";

        AllNotificationsOfEmployee allNotificationsOfEmployee = new AllNotificationsOfEmployee();
        String response = allNotificationsOfEmployee.getAllNotificationsOfEmployee(jsonData);

        assert response.equals("Error in fetching notifications");
    }

    @Test
    public void testInvalidEmployee(){
        String jsonData = "{\"employee_id\":\"AMP008\"}";
        String expectedResponse = "[]";

        AllNotificationsOfEmployee allNotificationsOfEmployee = new AllNotificationsOfEmployee();
        String response = allNotificationsOfEmployee.getAllNotificationsOfEmployee(jsonData);

        assert response.equals(expectedResponse);
    }
}
