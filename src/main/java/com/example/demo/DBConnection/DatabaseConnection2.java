package com.example.demo.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection2 {
    private static final String URL = "jdbc:sqlserver://DESKTOP-FCT9HJS;databaseName=test2";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
