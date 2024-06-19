package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.service.Authentication;
import org.junit.jupiter.api.Test;

public class TestAuthentication {
    @Test
    public void testUserAuthenticationNegative(){
        String employeeId = "InvalidEmployeeId";
        String password = "InvalidPassword";

        Authentication authentication = new Authentication();

        try{
            String response = authentication.authenticateUser(employeeId, password);
            assert(response.equals("Wrong username or password."));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalidEmployeeId(){
        String jsonData = "{\"employeeID\": \"InvalidEmployeeId\", \"password\": \"InvalidPassword\"}";

        Authentication authentication = new Authentication();
        String response = authentication.authenticate(jsonData);

        assert(response.equals("EmployeeID does not exist."));
    }

    @Test
    public void testInvalidJsonFormat(){
        String jsonData = "{\"employee_id\": \"InvalidEmployeeId\", \"password\": \"InvalidPassword\"}";

        Authentication authentication = new Authentication();
        String response = authentication.authenticate(jsonData);
        System.out.println(response);

        assert(response.equals("Wrong request format."));
    }
}
