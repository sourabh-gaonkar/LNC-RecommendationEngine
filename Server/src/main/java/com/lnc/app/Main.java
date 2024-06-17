package com.lnc.app;

import java.util.logging.Logger;

import com.lnc.connection.Server;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        try {
            Server.runServer();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
