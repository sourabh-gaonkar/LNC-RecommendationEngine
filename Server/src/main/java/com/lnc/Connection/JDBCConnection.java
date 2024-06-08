package com.lnc.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    private static JDBCConnection instance;
    private final Connection connection;

    private JDBCConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_URL = "jdbc:mysql://" + System.getenv("DB_URL") + ":3306/algo";
            String DB_USERNAME = System.getenv("DB_USERNAME");
            String DB_PASSWORD = System.getenv("DB_PASSWORD");
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
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
