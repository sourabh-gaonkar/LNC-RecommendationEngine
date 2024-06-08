import com.lnc.service.Registration;
import org.junit.jupiter.api.Test;

public class TestApp {
    @Test
    public void testCode() {
        String jsonData = "{\"name\":\"Jay Won\",\"role\":\"EMPLOYEE\",\"email\":\"gsjhdj@gmail.com\",\"password\":\"eIBF5PN63upZgDuovLb2y+vsq9mtZhTHlMdtqobbE+I=\",\"employeeID\":\"EMP098\"}";

        try {
            Registration registration = new Registration();
            String response = registration.addUser(jsonData);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
