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
    private static final String USER="root";
    private static final String PASSWORD="QueenCV@16";
    private static final String URL="jdbc:mysql://localhost:3307/u25020880_u24702791_chinook";
    
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
