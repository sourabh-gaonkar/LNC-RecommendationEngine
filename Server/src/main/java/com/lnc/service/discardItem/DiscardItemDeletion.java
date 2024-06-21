package com.lnc.service.discardItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.DiscardMenuQueries;
import com.lnc.DB.MenuQueries;
import com.lnc.utils.ConversionFromJson;

import java.util.logging.Logger;

public class DiscardItemDeletion {
    private final Logger logger = Logger.getLogger(DiscardItemDeletion.class.getName());
    private final ConversionFromJson conversionFromJson = new ConversionFromJson();
    private final DiscardMenuQueries discardMenuQueries = new DiscardMenuQueries();
    private final MenuQueries menuQueries = new MenuQueries();
    public String deleteDiscardedItem(String jsonData) {
        try{
            String itemName = conversionFromJson.getJsonValue("itemName", jsonData);

            if(discardMenuQueries.removeFromDiscardMenu(itemName)){
                logger.info("Item removed from discard menu");
                if(menuQueries.deleteMenuItem(itemName)){
                    logger.info("Item added back to menu");
                    return "Deleted item from discard menu.";
                } else {
                    logger.severe("Failed to add item back to menu");
                }
            } else {
                logger.severe("Failed to remove item from discard menu");
            }
            return "Failed to delete item from discard menu.";
        } catch (JsonProcessingException | NullPointerException e){
            logger.severe("Error in converting discarded item to JSON" + e.getMessage());
            return "Failed to delete item from discard menu.";
        }
    }
}
