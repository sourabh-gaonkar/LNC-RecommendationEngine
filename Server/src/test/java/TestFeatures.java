import com.lnc.DB.MenuRolloutQueries;
import java.util.List;
import java.util.Map;

import com.lnc.DB.RecommendationEngineQueries;
import org.junit.jupiter.api.Test;

public class TestFeatures {
    @Test
    public void testVoting() {

        try{
            RecommendationEngineQueries engineData = new RecommendationEngineQueries();
            Map<String, List<Map<String, Object>>> dataFrames = engineData.getAllData();
            for (Map.Entry<String, List<Map<String, Object>>> entry : dataFrames.entrySet()) {
                if(entry.getKey().equals("last_rollout")){
                    for (Map<String, Object> row : entry.getValue()) {
                        System.out.println(row);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
