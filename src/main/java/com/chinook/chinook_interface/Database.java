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
    public static Connection getDBConnection(){
        String url= "jdbc:mysql://localhost:3307/u25020880_u24702791_chinook";
        String user = "root";
        String password= "300kW0rm!";

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
