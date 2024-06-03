package com.lnc.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.auth.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                ) {
            String request;
            while((request = in.readLine())!= null) {
                System.out.println("Received: " + request);
                String response = handleRequest(request);
                out.println(response);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String handleRequest(String request) throws Exception {
        Redirection redirection = new Redirection();
        return redirection.redirect(request);
    }
}
