/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.sql.DriverManager;
import java.sql.Connection;

/**
 *
 * @author HP
 */
public class Conexion {
    
    //String url="jdbc:sqlserver://DESKTOP-QRAUFGK:1433;databaseName=labdsi;IntegratedSecurity=true;";
    String url = "jdbc:mysql://localhost:3306/labdsi";
    String user = "root";
    String password = "";
    Connection con;
    
    public Connection getCon() {
        
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //con = DriverManager.getConnection(url);
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        }
        catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return con;
    }
    
}
