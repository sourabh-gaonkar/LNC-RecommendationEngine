package com.lnc.service;

import static java.lang.Thread.sleep;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.connection.ServerConnection;
import com.lnc.model.Notification;
import com.lnc.util.ToJsonConversion;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NotificationService {

  private final String ERROR_MESSAGE = "Error in fetching notifications";

  public void fetchAndDisplayNotifications(String employeeID) throws Exception {
    String apiPath = "/getNotifications";
    String request = apiPath + "&" + new ToJsonConversion().codeEmployeeID(employeeID);

    String response = ServerConnection.requestServer(request);

    if (response.equals(ERROR_MESSAGE)) {
      System.out.println(ERROR_MESSAGE);
      return;
    }

    ObjectMapper objectMapper = new ObjectMapper();
    Queue<Notification> notificationQueue = new LinkedList<>(
            objectMapper.readValue(response, new TypeReference<List<Notification>>() {}));

    int serialNumber = 0;
    while (!notificationQueue.isEmpty()) {
      serialNumber++;
      Notification notification = notificationQueue.poll();
      if (notification != null) {
        printFormattedNotification(serialNumber, notification);
      }
      try {
        sleep(2000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private void printFormattedNotification(int serialNumber, Notification notification) {
    String formattedNotification = String.format("|  %d.   %s,   %s  |",
            serialNumber, notification.getMessage(), notification.getCreatedAt());
    String dashes = "-".repeat(formattedNotification.length() - 2);

    System.out.println(" " + dashes);
    System.out.println(formattedNotification);
    System.out.println(" " + dashes + "\n");
  }
}
