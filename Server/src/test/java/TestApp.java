import com.lnc.DB.MenuRolloutQueries;
import com.lnc.DB.NotificationQueries;
import com.lnc.model.Notification;
import com.lnc.service.Registration;
import com.lnc.service.employee.AllNotificationsOfEmployee;
import com.lnc.service.employee.MenuVote;
import com.lnc.service.employee.TodaysMenu;
import com.lnc.service.employee.TomorrowsMenu;
import com.lnc.service.sentimentAnalysis.SentimentAnalysis;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class TestApp {
    @Test
    public void testCode() {
        String jsonData = "[\"Congee with Pickles\",\"Pulao\",\"Shrimp Tempura\",\"Butter Chicken\"]";

        try{
            MenuVote menuVote = new MenuVote();
            String response = menuVote.voteForMenu(jsonData);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
