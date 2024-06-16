package com.lnc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnc.connection.ServerConnection;
import com.lnc.model.Notification;
import com.lnc.util.ToJsonConversion;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class AllNewNotifications {
    public void getNewNotifications(String employeeID) throws Exception {
        ToJsonConversion convertToJson = new ToJsonConversion();
        String jsonsString = convertToJson.codeEmployeeID(employeeID);

        String apiPath = "/getNotifications";
        String request = apiPath+ "&" + jsonsString;

        String response = ServerConnection.requestServer(request);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Notification> notifications = objectMapper.readValue(response, new TypeReference<List<Notification>>() {});

        Queue<Notification> notificationQueue = new LinkedList<>(notifications);

        int serialNumber = 0;
        while (!notificationQueue.isEmpty()) {
            serialNumber++;
            Notification notification = notificationQueue.poll();
            if (notification != null) {
                String formattedNotification = "|  " + serialNumber + ".   " + notification.getMessage() + ",   " + notification.getCreatedAt() + "  |";
                String dashes = "-".repeat(formattedNotification.length() - 2);

                System.out.println(" " + dashes);
                System.out.println(formattedNotification);
                System.out.println(" " + dashes + "\n");
            }
            sleep(2000);
        }
    }
}
