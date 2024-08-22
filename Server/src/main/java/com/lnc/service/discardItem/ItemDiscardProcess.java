package com.lnc.service.discardItem;

import com.lnc.DB.DiscardMenuQueries;
import com.lnc.DB.FeedbackQueries;
import com.lnc.DB.MenuQueries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ItemDiscardProcess {

    private final Logger logger = Logger.getLogger(ItemDiscardProcess.class.getName());
    private final MenuQueries menuQueries;
    private final FeedbackQueries feedbackQueries;
    private final DiscardMenuQueries discardMenuQueries;

    private static final List<String> NEGATIVE_PHRASES = Arrays.asList(
            "bad experience", "very poor", "not tasty", "too salty",
            "tasteless", "worst", "disgusting", "terrible", "awful", "inedible"
    );

    public ItemDiscardProcess() {
        this.menuQueries = new MenuQueries();
        this.feedbackQueries = new FeedbackQueries();
        this.discardMenuQueries = new DiscardMenuQueries();
    }

    public boolean processDiscardList() {
        List<Integer> menuItemIDs = menuQueries.getAllMenuIds();
        Map<Integer, Integer> itemNegativeCount = initializeItemNegativeCount(menuItemIDs);

        List<Map<String, Object>> feedbackList = feedbackQueries.getFeedbacksFromLastMonth();
        updateItemNegativeCount(feedbackList, itemNegativeCount);

        return updateDiscardMenu(itemNegativeCount);
    }

    private Map<Integer, Integer> initializeItemNegativeCount(List<Integer> menuItemIDs) {
        Map<Integer, Integer> itemNegativeCount = new HashMap<>();
        for (int itemID : menuItemIDs) {
            itemNegativeCount.put(itemID, 0);
        }
        return itemNegativeCount;
    }

    private void updateItemNegativeCount(List<Map<String, Object>> feedbackList, Map<Integer, Integer> itemNegativeCount) {
        for (Map<String, Object> feedback : feedbackList) {
            int itemId = (int) feedback.get("item_id");
            String comment = (String) feedback.get("comment");

            if (containsNegativePhrase(comment)) {
                itemNegativeCount.put(itemId, itemNegativeCount.getOrDefault(itemId, 0) + 1);
            }
        }
    }

    private boolean updateDiscardMenu(Map<Integer, Integer> itemNegativeCount) {
        boolean isDiscardMenuUpdated = true;

        for (Map.Entry<Integer, Integer> entry : itemNegativeCount.entrySet()) {
            if (entry.getValue() >= 3) {
                if (discardMenuQueries.addToDiscardMenu(entry.getKey())) {
                    logger.info("Item added to discard menu.");
                } else {
                    logger.severe("Failed to add item to discard menu.");
                    isDiscardMenuUpdated = false;
                    break;
                }
            }
        }

        return isDiscardMenuUpdated;
    }

    private boolean containsNegativePhrase(String comment) {
        if (comment == null || comment.isEmpty()) {
            return false;
        }

        String lowerCaseComment = comment.toLowerCase();
        for (String phrase : NEGATIVE_PHRASES) {
            if (lowerCaseComment.contains(phrase)) {
                return true;
            }
        }

        return false;
    }
}
