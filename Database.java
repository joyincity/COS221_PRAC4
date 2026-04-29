/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package u25020880_u24702791_chinook;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author DELL LATITUDE 7200
 */

public class Database {

    public static Connection getConnection() throws SQLException {
        // Read all six required environment variables
        String proto = System.getenv("CHINOOK_DB_PROTO");       // should be "mysql"
        String host = System.getenv("CHINOOK_DB_HOST");        // e.g., "localhost"
        String port = System.getenv("CHINOOK_DB_PORT");        // e.g., "3306"
        String dbName = System.getenv("CHINOOK_DB_NAME");      // your database name
        String username = System.getenv("CHINOOK_DB_USERNAME");
        String password = System.getenv("CHINOOK_DB_PASSWORD");

        // Build JDBC URL: jdbc:mysql://localhost:3306/database_name
        String url = String.format("jdbc:%s://%s:%s/%s", proto, host, port, dbName);

        return DriverManager.getConnection(url, username, password);
    }
}
