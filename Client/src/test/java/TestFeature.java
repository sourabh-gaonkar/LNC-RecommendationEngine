import com.lnc.model.EmployeeProfile;
import com.lnc.service.regiteration.EmployeeProfileFetcher;

public class TestFeature {
    public static void main(String[] args) {
        try{
            EmployeeProfileFetcher employeeProfileFetcher = new EmployeeProfileFetcher();
            EmployeeProfile employeeProfile = employeeProfileFetcher.getEmployeeProfile("EMP999");

            System.out.println("Employee diet preference: " + employeeProfile.getDietPreference());
            System.out.println("Employee spice level preference: " + employeeProfile.getSpiceLevel());
            System.out.println("Employee regional preference: " + employeeProfile.getRegionalPreference());
            System.out.println("Employee sweet tooth preference: " + employeeProfile.isSweetTooth());
            System.out.println("Employee ID: " + employeeProfile.getEmployeeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
