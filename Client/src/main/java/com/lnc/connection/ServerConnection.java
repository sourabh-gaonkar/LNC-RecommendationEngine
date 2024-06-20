package com.lnc.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerConnection {
  private static final Logger logger = Logger.getLogger(ServerConnection.class.getName());
  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 9999;
  private static BufferedReader in;
  private static PrintWriter out;

  public static String requestServer(String request) {
    try {
      Socket socket = openSocket(SERVER_PORT);
      openConnection(socket);

      out.println(request);
      String response = in.readLine();

      in.close();
      out.close();
      socket.close();

      return response;
    } catch (IOException ex) {
      logger.severe("Error in server connection: " + ex.getMessage());
      return null;
    }
  }

  private static Socket openSocket(int port) throws IOException {
    return new Socket(SERVER_ADDRESS, port);
  }

  private static void openConnection(Socket socket) throws IOException {
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
  }
}
