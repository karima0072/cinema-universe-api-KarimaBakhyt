package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/cinema_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("CONNECTED TO DATABASE");
            return connection;
        } catch (SQLException e) {
            System.out.println("FAILED TO CONNECT TO DATABASE");
            e.printStackTrace();
            return null;
        }
    }
}
