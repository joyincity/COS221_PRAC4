
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class TestDBConnection {
    public static void main(String[] args) {
        String url= "jdbc:mysql://localhost:3307/u25020880_u24702791_chinook";
        String user = "root";
        String password= "QueenCV@16";

        try(Connection con= DriverManager.getConnection(url,user,password)){
            if(con!=null){
                System.out.println("Successfully connected to database");

            }
        }catch(SQLException e){
            System.out.println(e.getMessage());


        }
    }
}