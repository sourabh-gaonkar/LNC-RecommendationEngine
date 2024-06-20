import com.lnc.DB.DiscardMenuQueries;
import com.lnc.service.discardItem.ItemDiscard;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestFeatures {
    @Test
    public void testVoting() {

        try{
            DiscardMenuQueries discardMenuQueries = new DiscardMenuQueries();
            List<String> discardedItems = discardMenuQueries.getAllDiscardedItems();
            for (String discardedItem : discardedItems) {
                ItemDiscard itemDiscard = new ItemDiscard();
                itemDiscard.getDiscardItemList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
