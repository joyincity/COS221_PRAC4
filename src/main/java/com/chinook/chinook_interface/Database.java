/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chinook.chinook_interface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Felicity Unathi Moyo
 */

public class Database {

    public static Connection getDBConnection() {

    //    String proto = System.getenv("CHINOOK_DB_PROTO");
        String host = System.getenv("CHINOOK_DB_HOST");
        String port = System.getenv("CHINOOK_DB_PORT");
        String dbName = System.getenv("CHINOOK_DB_NAME");
        String username = System.getenv("CHINOOK_DB_USERNAME");
        String password = System.getenv("CHINOOK_DB_PASSWORD");

        String url = proto + "://" + host + ":" + port + "/" + dbName;

        try {
            Connection con = DriverManager.getConnection(url, username, password);

            if (con != null) {
                System.out.println("Database connected successfully");
            }

            return con;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
