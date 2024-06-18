package com.lnc.connection;

import com.lnc.app.Route;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String request;
            while((request = in.readLine())!= null) {
                logger.log(Level.INFO, "Request received: {0} ", request);
                String response = handleRequest(request);
                out.println(response);
            }
        } catch (Exception ex) {
            logger.severe("Error in client handler: " + ex.getMessage());
        }
    }

    private String handleRequest(String request) throws Exception {
        Route redirection = new Route();
        return redirection.redirect(request);
    }
}
