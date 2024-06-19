import com.lnc.DB.MenuRolloutQueries;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class TestFeatures {
    @Test
    public void testVoting() {

        try{
            MenuRolloutQueries menuRolloutQueries = new MenuRolloutQueries();
            List<Map<String, Object>> todaysMenu = menuRolloutQueries.getTodaysMenu();
            for (Map<String, Object> item : todaysMenu) {
                System.out.println(item.get("average_rating"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
