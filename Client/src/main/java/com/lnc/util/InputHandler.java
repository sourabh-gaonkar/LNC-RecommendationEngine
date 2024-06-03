package com.lnc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {
    private static final InputStreamReader inputReader = new InputStreamReader(System.in);
    private static final BufferedReader reader = new BufferedReader(inputReader);

    public static String getString(String prompt) throws IOException {
        System.out.print(prompt);
        return reader.readLine();
    }

    public static int getInt(String prompt) throws IOException {
        while(true) {
            try {
                return Integer.parseInt(getString(prompt));
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid input.\n");
            }
        }
    }

    public static double getDouble(String prompt) throws IOException {
        while(true) {
            try {
                return Double.parseDouble(getString(prompt));
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid input.\n");
            }
        }
    }

    public static void closeInputHandler() throws IOException {
        reader.close();
        inputReader.close();
    }
}