package com.iSoftTech.logbookDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dada abiola
 */
public class DBConnection {
    public Connection conn;
    //SQLite Connection String
    String url = "jdbc:sqlite:logbook.sqlite";
    
    public Connection Connector(){
        
        try {
            Class.forName("org.sqlite.JDBC");
            conn = (Connection) DriverManager.getConnection(url);
            return conn;
            
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            return null;
        }
    }
    
    
    public Connection geConnection(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);

        } catch (ClassNotFoundException | SQLException ex) {

            System.out.println(ex.getMessage());
        }
        
        return conn;
    }
}
