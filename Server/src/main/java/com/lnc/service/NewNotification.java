package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.NotificationQueries;
import com.lnc.model.Notification;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;
import java.util.List;
import java.util.logging.Logger;

public class NewNotification {
  private final Logger logger = Logger.getLogger(NewNotification.class.getName());
  public String getNewNotifications(String jsonData) {
    try{
      ConversionFromJson fromJsonConverter = new ConversionFromJson();
      String employee_id = fromJsonConverter.getJsonValue("employee_id", jsonData);

      NotificationQueries notificationQueries = new NotificationQueries();
      List<Notification> notifications = notificationQueries.getNewNotifications(employee_id);

      ConversionToJson toJson = new ConversionToJson();
      return toJson.codeNotifications(notifications);
    } catch (JsonProcessingException | NullPointerException e) {
      logger.severe("Error getting notification: " + e.getMessage());
      return null;
    }
  }
}
