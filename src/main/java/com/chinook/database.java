package com.chinook;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class database {
    public static Connection getDBConnection() {
        String url= "jdbc:mysql://localhost:3307/u25020880_u24702791_chinook";
        String user = "root";
        String password= "your password";

        try{
            Connection con= DriverManager.getConnection(url,user,password);
            
                System.out.println("Successfully connected to database");
                return con;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            

        }
        return null;
    }
}
