package com.lnc.service.discardItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.DiscardMenuQueries;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.logging.Logger;

public class ItemDiscard {

    private static final Logger logger = Logger.getLogger(ItemDiscard.class.getName());
    private final ItemDiscardProcess itemDiscardProcess;
    private final DiscardMenuQueries discardMenuQueries;
    private final ConversionToJson toJsonConverter;

    public ItemDiscard() {
        itemDiscardProcess = new ItemDiscardProcess();
        discardMenuQueries = new DiscardMenuQueries();
        toJsonConverter = new ConversionToJson();
    }

    public String getDiscardItemList() {
        try {
            if (!itemDiscardProcess.processDiscardList()) {
                logger.severe("Failed to process discard list.");
                return "Failed to process discard list.";
            }

            List<String> discardedItems = discardMenuQueries.getAllDiscardedItems();
            return toJsonConverter.codeDiscardedItems(discardedItems);
        } catch (JsonProcessingException | NullPointerException e) {
            logger.severe("Failed to convert discarded items to JSON: " + e.getMessage());
            return "Error converting discarded items to JSON.";
        }
    }
}
