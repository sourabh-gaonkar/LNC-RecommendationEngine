package com.lnc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
  private static JDBCConnection instance;
  private final Connection connection;

  private JDBCConnection() throws SQLException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String dbUrl = "jdbc:mysql://" + System.getenv("DB_URL") + ":3306/algo";
      String dbUsername = System.getenv("DB_USERNAME");
      String dbPassword = System.getenv("DB_PASSWORD");
      this.connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    } catch (ClassNotFoundException e) {
      throw new SQLException(e);
    }
  }

  public static JDBCConnection getInstance() throws SQLException {
    if (instance == null || instance.getConnection().isClosed()) {
      instance = new JDBCConnection();
    }

    return instance;
  }

  public Connection getConnection() {
    return connection;
  }
}
