package chack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles creating connections to the database.
 */
public class ConnectionDb {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ToDo;"
            + "encrypt=true;"
            + "trustServerCertificate=true";
    private static final String USER = "test_user";
    private static final String PASSWORD = "1234";

    /**
     * Get a new connection to the database.
     * @return Connection object
     * @throws SQLException if the connection fails
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connected successfully");
        return conn;
    }
}