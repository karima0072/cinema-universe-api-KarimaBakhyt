package utils;

import java.sql.Connection;

public class TestJdbc {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("DB CONNECTION SUCCESS");
        } else {
            System.out.println("DB CONNECTION FAILED");
        }
    }
}
