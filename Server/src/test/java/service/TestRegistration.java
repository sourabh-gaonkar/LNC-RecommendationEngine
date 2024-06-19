package service;

import com.lnc.service.Registration;
import org.junit.jupiter.api.Test;

public class TestRegistration {
    @Test
    public void testInvalidJsonData() {
        String jsonData = "{\"name\":\"Test User\",\"role\":\"EMPLOYEE\",\"gmail\":\"test@gmail.com\",\"password\":\"tL0pSAqxlvqnguDU7NEML0ISgUEFIn5feZL1v0shKmQ=\",\"employeeID\":\"EMP998\"}";

        Registration registration = new Registration();
        String response = registration.addUser(jsonData);

        assert(response.equals("Invalid data format."));
    }

    @Test
    public void testExistingEmployeeAddition() {
        String jsonData = "{\"name\":\"Test User\",\"role\":\"EMPLOYEE\",\"email\":\"test@gmail.com\",\"password\":\"tL0pSAqxlvqnguDU7NEML0ISgUEFIn5feZL1v0shKmQ=\",\"employeeID\":\"EMP998\"}";

        Registration registration = new Registration();
        String response = registration.addUser(jsonData);

        assert(response.equals("Unable to add employee."));
    }
}
