package service;

import com.lnc.service.NewNotification;
import org.junit.jupiter.api.Test;

public class TestNewNotification {
    @Test
    public void testInvalidJson() {
        String jsonData = "{\"employee_id\": \"1\"}";

        NewNotification newNotification = new NewNotification();
        String result = newNotification.getNewNotifications(jsonData);

        assert(result.equals("[]"));
    }
}
