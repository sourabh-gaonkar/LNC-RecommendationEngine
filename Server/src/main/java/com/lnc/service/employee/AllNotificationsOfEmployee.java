package com.lnc.service.employee;

import com.lnc.DB.NotificationQueries;
import com.lnc.model.Notification;
import com.lnc.utils.FromJson;
import com.lnc.utils.ToJson;
import java.util.List;

public class AllNotificationsOfEmployee {
  public String getAllNotificationsOfEmployee(String jsonData) throws Exception {
    FromJson fromJson = new FromJson();
    String employeeId = fromJson.getJsonValue("employee_id", jsonData);

    NotificationQueries notificationQueries = new NotificationQueries();
    List<Notification> notifications = notificationQueries.getAllUserNotifications(employeeId);

    ToJson toJson = new ToJson();
    return toJson.codeNotifications(notifications);
  }
}
