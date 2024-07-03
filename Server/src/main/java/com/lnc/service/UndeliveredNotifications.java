package com.lnc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.NotificationQueries;
import com.lnc.model.Notification;
import com.lnc.utils.ConversionFromJson;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.logging.Logger;

public class UndeliveredNotifications {
  private final Logger logger = Logger.getLogger(UndeliveredNotifications.class.getName());
  private final ConversionFromJson fromJsonConverter = new ConversionFromJson();
  private final NotificationQueries notificationQueries = new NotificationQueries();
  private final ConversionToJson toJson = new ConversionToJson();

  public String getNewNotifications(String jsonData) {
    try{
      String employee_id = fromJsonConverter.getJsonValue("employee_id", jsonData);

      List<Notification> notifications = notificationQueries.getNewNotifications(employee_id);

      return toJson.codeNotifications(notifications);

    } catch (JsonProcessingException | NullPointerException e) {
      logger.severe("Error getting notification: " + e.getMessage());
      return null;
    }
  }
}
