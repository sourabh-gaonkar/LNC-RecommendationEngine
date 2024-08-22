package com.lnc.service.discardedItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.util.JsonDataFormat;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscardItemDisplay {
    private static final Logger logger = Logger.getLogger(DiscardItemDisplay.class.getName());
    private static final String API_PATH = "/getDiscardItems";
    private final JsonDataFormat jsonFormatter;

    public DiscardItemDisplay() {
        this.jsonFormatter = new JsonDataFormat();
    }

    public List<String> displayDiscardItems() {
        String request = API_PATH + "& ";
        String response = ServerConnection.requestServer(request);

        if (response.equals("null") || response.equals("Error getting discarded items.") || response.equals("Failed to process discard list.")) {
            logger.log(Level.SEVERE, "Error getting discarded items: {0}", response);
            return null;
        }

        try {
            return jsonFormatter.printDiscardedItems(response);
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "JSON processing error while displaying discarded items: {0}", e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error while displaying discarded items: {0}", e.getMessage());
        }

        return null;
    }
}
