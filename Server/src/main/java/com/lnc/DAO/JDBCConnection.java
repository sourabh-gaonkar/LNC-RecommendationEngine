package com.lnc.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    private static JDBCConnection instance;
    private final Connection connection;
    private final String url = "jdbc:mysql://lnc.cleo0cmg6k5d.ap-southeast-2.rds.amazonaws.com:3306/test";
    private final String username = "sourabh";
    private final String password = "Intimet3c!";

    private JDBCConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public static JDBCConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new JDBCConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new JDBCConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}