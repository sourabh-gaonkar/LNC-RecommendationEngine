package service.chef;

import com.lnc.service.chef.FeedbackDisplay;
import org.junit.jupiter.api.Test;

public class TestFeedbackDisplay {
    @Test
    public void testUnavailableItem() {
        String jsonData = "{\"itemName\":\"Imaginary Item\"}";

        FeedbackDisplay feedbackDisplay = new FeedbackDisplay();
        String response = feedbackDisplay.displayFeedback(jsonData);

        assert(response.equals("Invalid item name."));
    }

    @Test
    public void testInvalidJsonData() {
        String jsonData = "{\"item\":\"Idli\"}";

        FeedbackDisplay feedbackDisplay = new FeedbackDisplay();
        String response = feedbackDisplay.displayFeedback(jsonData);

        assert(response.equals("Invalid item name."));
    }
}
