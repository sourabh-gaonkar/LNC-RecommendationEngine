package com.lnc.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private static final int PORT = 9999;
  private static final ExecutorService pool = Executors.newFixedThreadPool(10);

  public static void runServer() throws IOException {
    ServerSocket serverSocket = new ServerSocket(PORT);
    System.out.println("Server started...");

    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("Client connected: " + clientSocket.getInetAddress());
      pool.execute(new ClientHandler(clientSocket));
    }
  }
}
