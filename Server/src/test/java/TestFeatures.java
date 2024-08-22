import com.lnc.DB.*;
import com.lnc.model.Feedback;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class TestFeatures {
    @Test
    public void testFeatures() {
        try {
            MenuQueries menuQueries = new MenuQueries();
            List<Map<String, Object>> menu = menuQueries.viewMenuItems();
            System.out.println(menu);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
