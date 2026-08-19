package com.sunrise.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/sqlworkbench"
            + "?useSSL=false"
            + "&serverTimezone=UTC"
            + "&allowPublicKeyRetrieval=true";

    private static final String USER = "root";

    private static final String PASSWORD = "root";

    private DBConnection() {
    }

    public static Connection getConnection()
            throws SQLException {

        try {
            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );
        } catch (ClassNotFoundException e) {

            throw new SQLException(
                    "MySQL JDBC Driver not found",
                    e
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}