import com.lnc.DB.NotificationQueries;
import com.lnc.model.Notification;
import com.lnc.service.Registration;
import com.lnc.service.employee.AllNotificationsOfEmployee;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestApp {
    @Test
    public void testCode() {
        String employee_id = "{\"employee_id\":\"EMP001\"}";

        try{
            AllNotificationsOfEmployee allNotificationsOfEmployee = new AllNotificationsOfEmployee();
            allNotificationsOfEmployee.getAllNotificationsOfEmployee(employee_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
