/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iSoftTech.logbook.view;

import com.iSoftTech.logbookDB.DBConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author Dada abiola
 */
public class IncomeChartController implements Initializable {
    
    DBConnection conn = new DBConnection();
    PreparedStatement pS = null;
    ResultSet rS = null;
    private final ObservableList data = FXCollections.observableArrayList();
    @FXML
    private PieChart incomeChart;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadChartStock();
        incomeChart.getData().addAll(data);
    }    
    public void loadChartStock() {
        String query = "select transactionFee, transactionType FROM buzzDB ORDER BY transactionFee";
        try{
            pS = conn.Connector().prepareStatement(query);
            rS = pS.executeQuery();
                while(rS.next()){
                    data.add(new PieChart.Data(rS.getString("transactionType"), rS.getInt("transactionFee")));
                }

        }catch (Exception e){
        }finally{
                      try{  
                        //conn.close();
                        pS.close();
                        rS.close();
                      }catch(Exception e){
                          
                      }
                }
    }
}
