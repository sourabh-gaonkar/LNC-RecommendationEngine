import com.lnc.DB.DiscardMenuQueries;
import com.lnc.DB.ImproviseFeedbackSessionQueries;
import com.lnc.DB.ImproviseMenuQueries;
import com.lnc.service.discardItem.ItemDiscard;
import com.lnc.service.employee.improviseItem.AnswerSubmission;
import com.lnc.service.employee.improviseItem.ImproviseItemList;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestFeatures {
    @Test
    public void testVoting() {
        String jsonData = "{\"answers\":[{\"7\":\"taste and consistency\"},{\"8\":\"spicy and creamy\"},{\"9\":\"refer here some cookbook or youtube video\"}],\"employeeId\":\"EMP001\"}";

        try{
            AnswerSubmission answerSubmission = new AnswerSubmission();
            String response = answerSubmission.submitAnswer(jsonData);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
