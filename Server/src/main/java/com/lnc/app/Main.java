package com.lnc.app;

import com.lnc.Connection.Server;

public class Main {
    public static void main(String[] args) {
        try {
            Server.runServer();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
