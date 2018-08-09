package com.iSoftTech.logbookDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dada abiola
 */
public class DBModel {
    
    PreparedStatement pst;
    private static boolean hasData = false;
    DBConnection conn = new DBConnection();
    
    public void createNewDatabase() throws SQLException{
        
        if(!hasData){
            hasData = true;
            
                //SQL statement for creating a table login
                String sql1 = "CREATE TABLE IF NOT EXISTS login(id INTEGER PRIMARY KEY, username text NOT NULL, password text NOT NULL);";
           
                //SQL statement for creating a table transaction(buzzDB)
                String sql2 = "CREATE TABLE IF NOT EXISTS buzzDB(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "transactionType text NOT NULL, customer text, commission double, float double, transactionAmount double,"
                        + "transactionFee integer, phone text, recipientName text, recipientPhone text, account text, transactionID text,"
                        + "date text, time text, bank text)";
                
                try{
                    pst = conn.Connector().prepareStatement(sql1);
                    pst.execute();
      
                    pst = conn.Connector().prepareStatement(sql2);
                    pst.execute();
                    
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }finally {
                pst.close();
                }
        }
    }
    
    public void insertUser() throws SQLException{
       
        try {
            //Inserting Data
            pst = conn.Connector().prepareStatement("INSERT INTO login VALUES(?,?,?);");
            pst.setInt(1, 1);
            pst.setString(2, "Admin");
            pst.setString(3, "password");
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public boolean adminLogin(String user, String pass) throws SQLException{
        
        PreparedStatement pS=null;
        ResultSet rS=null;
        String query = "select * from login where username = ? and password = ?";
        try{
            pS = conn.Connector().prepareStatement(query);
            pS.setString(1, user);
            pS.setString(2, pass);
            rS = pS.executeQuery();

            return rS.next();
            
        }catch (Exception e){
            return false;
        }finally {
            pS.close();
            rS.close();
        }
    }
    
}
