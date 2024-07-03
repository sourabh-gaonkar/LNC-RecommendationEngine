import com.lnc.DB.DiscardMenuQueries;
import com.lnc.DB.EmployeeOrderQueries;
import com.lnc.DB.EmployeeProfileQueries;
import com.lnc.DB.FeedbackQueries;
import com.lnc.model.Feedback;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class TestFeatures {
    @Test
    public void testFeatures() {
        try {
            FeedbackQueries fq = new FeedbackQueries();
            List<Map<String, Object>> report = fq.generateFeedbackReport("03", "2024");
            System.out.println(report);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
