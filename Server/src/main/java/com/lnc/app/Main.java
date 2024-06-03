package com.lnc.app;

public class Main {
    public static void main(String[] args) {
        try {
            Server.runServer();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
