import com.lnc.connection.ServerConnection;
import com.lnc.util.ToJsonConversion;
import org.junit.Test;

public class TestClient {
    @Test
    public void testServerConnection() {
        String request = "/login&{\"password\":\"6G94qKPK8LYNjnTllCqm2G3BUM08AzOK7yW30tfjrMc=\",\"employeeID\":\"ADM001\"}";

        int iterations = 5;

        while(iterations > 0) {
            try {
                ToJsonConversion toJsonConversion = new ToJsonConversion();
                String response = toJsonConversion.codeItemsCount(5);
                System.out.println("Request: " + response);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            iterations--;
        }
    }
}
