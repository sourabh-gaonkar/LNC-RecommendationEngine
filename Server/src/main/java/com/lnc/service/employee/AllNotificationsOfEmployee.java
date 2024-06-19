package com.lnc.service.employee;

import com.lnc.DB.NotificationQueries;
import com.lnc.model.Notification;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;
import java.util.List;

public class AllNotificationsOfEmployee {
  public String getAllNotificationsOfEmployee(String jsonData) throws Exception {
    ConversionFromJson fromJson = new ConversionFromJson();
    String employeeId = fromJson.getJsonValue("employee_id", jsonData);

    NotificationQueries notificationQueries = new NotificationQueries();
    List<Notification> notifications = notificationQueries.getAllUserNotifications(employeeId);

    ConversionToJson toJson = new ConversionToJson();
    return toJson.codeNotifications(notifications);
  }
}
