package com.lnc.service.regiteration;

import com.lnc.model.EmployeeProfile;
import com.lnc.util.InputHandler;

import java.io.IOException;

public class EmployeeProfileFetcher {
    public EmployeeProfile getEmployeeProfile(String employeeId) throws IOException {
        int dietPreferenceCode = getDietPreferenceCode();
        int spiceLevelCode = getSpiceLevelCode();
        int regionalPreferenceCode = getRegionalPreferenceCode();
        boolean sweetTooth = getSweetToothPreference();

        EmployeeProfile employeeProfile = new EmployeeProfile();
        employeeProfile.setEmployeeId(employeeId);
        employeeProfile.setDietPreference(dietPreferenceCode);
        employeeProfile.setSpiceLevel(spiceLevelCode);
        employeeProfile.setRegionalPreference(regionalPreferenceCode);
        employeeProfile.setSweetTooth(sweetTooth);

        return employeeProfile;
    }

    private int getDietPreferenceCode() throws IOException {
        int dietPreferenceCode;
        while (true) {
            dietPreferenceCode = InputHandler.getInt("\nSelect your diet preference:\n1. Veg\n2. Egg\n3. Non-Veg\nEnter your choice: ");
            if (dietPreferenceCode < 1 || dietPreferenceCode > 3) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                continue;
            }
            break;
        }
        return dietPreferenceCode;
    }

    private int getSpiceLevelCode() throws IOException {
        int spiceLevelCode;
        while (true) {
            spiceLevelCode = InputHandler.getInt("\nSelect your spice level preference:\n1. High\n2. Medium\n3. Low\nEnter your choice: ");
            if (spiceLevelCode < 1 || spiceLevelCode > 3) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                continue;
            }
            break;
        }
        return spiceLevelCode;
    }

    private int getRegionalPreferenceCode() throws IOException {
        int regionalPreferenceCode;
        while (true) {
            regionalPreferenceCode = InputHandler.getInt("\nSelect your regional preference:\n1. South Indian\n2. North Indian\n3. Pan-Asian\nEnter your choice: ");
            if (regionalPreferenceCode < 1 || regionalPreferenceCode > 3) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                continue;
            }
            break;
        }
        return regionalPreferenceCode;
    }

    private boolean getSweetToothPreference() throws IOException {
        String sweetToothPreference;
        while (true) {
            sweetToothPreference = InputHandler.getString("\nDo you have a sweet tooth? (yes/no): ");
            if (!sweetToothPreference.equalsIgnoreCase("yes") && !sweetToothPreference.equalsIgnoreCase("no")) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                continue;
            }
            break;
        }
        return sweetToothPreference.equalsIgnoreCase("yes");
    }
}
