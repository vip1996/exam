package chack;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;;

public class ConnectionDB { 
	private static String url = "jdbc:sqlserver://localhost:1433;databaseName=ToDo;"
            		     + "encrypt=true;"
            		     + "trustServerCertificate=true";
    private static String user = "test_user";
    private static  String password = "1234";
	
    public static Connection getconnection() {
          	
        try {
                        
           Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected Succesfully");            
            return conn;
            
        } catch (SQLException e) {
            System.out.println("Not Connected: " + e.getMessage());
            
        }
		return null; 
            }
        
    }
    
   


