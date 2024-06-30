package com.lnc.service.discardedItem;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.connection.ServerConnection;
import com.lnc.util.ToJsonConversion;

import java.util.logging.Logger;

public class DiscardedItemDeletion {
    private final Logger logger = Logger.getLogger(DiscardedItemDeletion.class.getName());
    private final ToJsonConversion toJsonConversion = new ToJsonConversion();
    public void deleteDiscardedItem(String discardedItem) {
        try{
            String request = toJsonConversion.codeItemName(discardedItem, "/discardItem/deleteItem");

            String response = ServerConnection.requestServer(request);
            System.out.println(response);
        } catch (JsonProcessingException | NullPointerException e){
            logger.severe("Error in converting discarded item to JSON");
        }
    }
}
