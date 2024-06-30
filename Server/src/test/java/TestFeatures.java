import com.lnc.DB.DiscardMenuQueries;
import com.lnc.DB.ImproviseFeedbackSessionQueries;
import com.lnc.DB.ImproviseMenuQueries;
import com.lnc.DB.MenuRolloutQueries;
import com.lnc.service.discardItem.ImproviseItemFeedbackFetcher;
import com.lnc.service.discardItem.ItemDiscard;
import com.lnc.service.employee.PreferenceSorter;
import com.lnc.service.employee.improviseItem.AnswerSubmission;
import com.lnc.service.employee.improviseItem.ImproviseItemList;
import com.lnc.service.registration.EmployeeProfileCreator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class TestFeatures {
    @Test
    public void testVoting() {
        String jsonData = "{\"dietPreference\":\"NON_VEG\",\"spiceLevel\":\"MEDIUM\",\"regionalPreference\":\"OTHER\",\"sweetTooth\":false,\"employeeId\":\"EMP999\"}";

        try {
            EmployeeProfileCreator creator = new EmployeeProfileCreator();
            String response = creator.createEmployeeProfile(jsonData);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
