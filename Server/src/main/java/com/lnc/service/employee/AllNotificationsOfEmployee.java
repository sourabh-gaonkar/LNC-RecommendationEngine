package com.lnc.service.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.NotificationQueries;
import com.lnc.model.Notification;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;
import java.util.List;
import java.util.logging.Logger;

public class AllNotificationsOfEmployee {
  private final Logger logger = Logger.getLogger(AllNotificationsOfEmployee.class.getName());
  private final ConversionFromJson fromJson = new ConversionFromJson();
  private final NotificationQueries notificationQueries = new NotificationQueries();
  private final ConversionToJson toJson = new ConversionToJson();

  public String getAllNotificationsOfEmployee(String jsonData) {
    try{
      String employeeId = fromJson.getJsonValue("employee_id", jsonData);

      List<Notification> notifications = notificationQueries.getAllUserNotifications(employeeId);

      return toJson.codeNotifications(notifications);
    } catch (JsonProcessingException | NullPointerException ex) {
      logger.severe("Error in fetching notifications: " + ex.getMessage());
      return "Error in fetching notifications";
    }
  }
}
