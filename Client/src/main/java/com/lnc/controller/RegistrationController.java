package com.lnc.controller;

import com.lnc.connection.ServerConnection;
import com.lnc.model.Employee;
import com.lnc.util.InputHandler;
import com.lnc.util.ToJsonConversion;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RegistrationController {
    public void registerUser() throws Exception {
        Employee employee = getEmployee();

        ToJsonConversion converter = new ToJsonConversion();
        String request = converter.codeUserDetails(employee);

        String response = ServerConnection.requestServer(request);
        System.out.println("Response: " + response);
    }

    private Employee getEmployee() throws IOException, NoSuchAlgorithmException {
        String name = getName();
        String employeeID = getEmployeeID();
        String emailID = getMailID();
        String password = getHashPassword(getPassword());

        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmployeeID(employeeID);
        employee.setEmail(emailID);
        employee.setPassword(password);
        return employee;
    }

    private String getName() throws IOException {
        String name;
        while (true) {
            name = InputHandler.getString("\nEnter your name: ");
            if (name.isEmpty()) {
                System.out.println(
                        "Name cannot be empty.");
                continue;
            }
            break;
        }
        return name;
    }

    private String getEmployeeID() throws IOException {
        String employeeID;
        while (true) {
            employeeID = InputHandler.getString("\nEnter your Employee ID: ");
            if (employeeID.isEmpty() || !isValidEmployeeID(employeeID)) {
                System.out.println(
                        "Invalid Employee ID. Please enter a valid Employee ID.");
                continue;
            }
            break;
        }
        return employeeID;
    }

    private boolean isValidEmployeeID(String employeeID) {
        if(employeeID.length() != 6 || !employeeID.startsWith("EMP")) {
            return false;
        }

        String numberID = employeeID.substring(3);

        return numberID.matches("\\d{3}");
    }

    private String getMailID() throws IOException {
        String emailID;
        while (true) {
            emailID = InputHandler.getString("\nEnter your mail ID [ only gmail ]: ");
            if (emailID.isEmpty() || !isValidEmailID(emailID)) {
                System.out.println(
                        "Invalid Email ID. Please enter a valid Email ID [ only gmail ].");
                continue;
            }
            break;
        }
        return emailID;
    }

    private boolean isValidEmailID(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) {
            return false;
        }

        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        if (!domainPart.equals("gmail.com")) {
            return false;
        }

        if (localPart.isEmpty()) {
            return false;
        }

        for (char c : localPart.toCharArray()) {
            if (!isAllowedLocalPartCharacter(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllowedLocalPartCharacter(char c) {
        return Character.isLetterOrDigit(c) || c == '.' || c == '_';
    }

    private String getPassword() throws IOException {
        String password;
        while (true) {
            password = InputHandler.getString("\nEnter your password: ");
            if (password.isEmpty() || !isValidPassword(password)) {
                System.out.println(
                        "Invalid Password.");
                continue;
            }
            break;
        }
        return password;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    private String getHashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
