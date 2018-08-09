package com.iSoftTech.logbook.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.iSoftTech.logbook.model.Transaction;
import com.iSoftTech.logbookDB.DBConnection;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class TransactionEditDialogController implements Initializable {

    @FXML
    private JFXTextField commDeuLabel;

    @FXML
    private JFXTextField eFLabel;

    @FXML
    private JFXTextField txnTypeLabel;

    @FXML
    private JFXTextField clientName;

    @FXML
    private JFXTextField reciNameLabel;
    
    @FXML
    private JFXTextField bankName;

    @FXML
    private JFXTextField txnAmountLabel;

    @FXML
    private JFXTextField txnFeeLabel;

    @FXML
    private JFXTextField phNumberLabel;

    @FXML
    private JFXTextField reciPhoneLabel;
    
    @FXML
    private JFXTextField reciAcctNumber;

    @FXML
    private Label reciTxnID;
    
     @FXML
    private Button saveData;

    @FXML
    private Button updateData;

    @FXML
    private JFXDatePicker datePickLabel;

    @FXML
    private JFXDatePicker timePickLabel;
    
    @FXML
    private Button btnClose;
    
    @FXML
    private Label dailyTxnLabel;

    private Stage dialogStage;
    private Transaction transaction;
    private boolean okClicked = false;
    DBConnection conn = new DBConnection();
    PreparedStatement pS = null;
    PreparedStatement hs = null;
    ResultSet rS = null;
    static String temp;
    private static final String ALPHA_NUM_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        reciTxnID.setText(randomAlphaNum(5));
    } 
    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    public static String randomAlphaNum(int count){
        
       StringBuilder builder = new StringBuilder();
       while(count-- != 0){
           int character = (int)(Math.random()*ALPHA_NUM_STRING.length());
           builder.append(ALPHA_NUM_STRING.charAt(character));
       }
       return builder.toString();
   }
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    public void setSaveDisable(){
        saveData.setDisable(true);
    }
    public void setUpdateDisable(){
        updateData.setDisable(true);
    }
    @FXML
    void handleSave(ActionEvent event) {
        if (isInputValid()){ 
            
                        String txnType = txnTypeLabel.getText();
                        String client = clientName.getText();
                        Double commission = Double.parseDouble(commDeuLabel.getText());
                        Double efloat = Double.parseDouble(eFLabel.getText());
                        Double amount = Double.parseDouble(txnAmountLabel.getText());
                        Integer fee = Integer.parseInt(txnFeeLabel.getText());
                        String phone = phNumberLabel.getText();
                        String reciientName = reciNameLabel.getText();
                        String recipientPhone = reciPhoneLabel.getText();
                        String recipientAccount = reciAcctNumber.getText();
                        String recipientTxnID = reciTxnID.getText();
                        Object dateE = datePickLabel.getValue();
                        Object time = timePickLabel.getTime();
                        String bank = bankName.getText();
                        
                        String query = "INSERT INTO buzzDB (transactionType, customer, commission, float,"
                                + " transactionAmount, transactionFee, phone, recipientName, "
                                + "recipientPhone, account, transactionID, date, time, bank) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        pS = null; 
                    try{
                        
                        pS = conn.Connector().prepareStatement(query);
                        pS.setString(1, txnType );
                        pS.setString(2, client);
                        pS.setDouble(3, commission);
                        pS.setDouble(4, efloat);
                        pS.setDouble(5, amount);
                        pS.setInt(6, fee);
                        pS.setString(7, phone);
                        pS.setString(8, reciientName);
                        pS.setString(9, recipientPhone);
                        pS.setString(10, recipientAccount);
                        pS.setString(11, recipientTxnID);
                        pS.setObject(12, dateE);
                        pS.setObject(13, time);
                        pS.setString(14, bank);
            		pS.execute();
         
                    } catch(SQLException e){
                    }finally{
                      try{  
                        pS.close();
                      }catch(Exception e){
                          
                      }
                    } 
                 okClicked = true;
                    if(okClicked)
                    { 
                        okClicked = true;
                            NotificationType notificationType = NotificationType.SUCCESS;
                            TrayNotification tray = new TrayNotification();
                            tray.setTitle("Congratulation!!!");
                            tray.setMessage("Data Successfully Added!.");
                            tray.setNotificationType(notificationType);
                            tray.showAndDismiss(Duration.millis(3000));
                            ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        
        if(isInputValid()){
            
                        String txnType = txnTypeLabel.getText();
                        String client = clientName.getText();
                        Double commission = Double.parseDouble(commDeuLabel.getText());
                        Double efloat = Double.parseDouble(eFLabel.getText());
                        Double amount = Double.parseDouble(txnAmountLabel.getText());
                        Integer fee = Integer.parseInt(txnFeeLabel.getText());
                        String phone = phNumberLabel.getText();
                        String reciientName = reciNameLabel.getText();
                        String recipientPhone = reciPhoneLabel.getText();
                        String recipientAccount = reciAcctNumber.getText();
                        String recipientTxnID = reciTxnID.getText();
                        Object dateE = datePickLabel.getValue();
                        Object time = timePickLabel.getTime();
                        String bank = bankName.getText();
                        
                        String data = "update buzzDB set transactionType = ?, customer = ?, commission =?, float=?,"
                                + " transactionAmount=?,transactionFee=?, phone=?, recipientName=?, "
                                + "recipientPhone=?, account=?, transactionID=?,date=?, time=?, bank=? where transactionID='"+temp+"'";
                try {     
                    pS = conn.Connector().prepareStatement(data);
               
                        pS.setString(1, txnType );
                        pS.setString(2, client);
                        pS.setDouble(3, commission);
                        pS.setDouble(4, efloat);
                        pS.setDouble(5, amount);
                        pS.setInt(6, fee);
                        pS.setString(7, phone);
                        pS.setString(8, reciientName);
                        pS.setString(9, recipientPhone);
                        pS.setString(10, recipientAccount);
                        pS.setString(11, recipientTxnID);
                        pS.setObject(12, dateE);
                        pS.setObject(13, time);
                        pS.setString(14, bank);                       
                        pS.executeUpdate();
                    
                } catch (SQLException ex) {
                }finally{
                      try{  
                        //conn.close();
                        pS.execute();
                        pS.close();
                      }catch(Exception e){
                          
                      }
                } 
                okClicked = true;
                    if(okClicked){
                        NotificationType notificationType = NotificationType.SUCCESS;
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("Congratulation!!!");
                        tray.setMessage("Transaction Data Successfully Updated.");
                        tray.setNotificationType(notificationType);
                        tray.showAndDismiss(Duration.millis(3000));
                    }
                    ((Node)(event.getSource())).getScene().getWindow().hide();
       }
             
    }
    public void setTransactionData(Transaction trans) {
        
        ResultSet rs;
        String query = "SELECT * FROM buzzDB where transactionID = '"+trans.getTxnID()+"'";
        try{
            pS = conn.Connector().prepareStatement(query);
            rs = pS.executeQuery();
            while(rs.next()){              
                txnTypeLabel.setText(rs.getString("transactionType"));
                clientName.setText(rs.getString("customer"));            
                commDeuLabel.setText(Double.toString(rs.getDouble("commission")));             
                eFLabel.setText(Double.toString(rs.getDouble("float")));             
                txnAmountLabel.setText(Double.toString(rs.getDouble("transactionAmount")));             
                txnFeeLabel.setText(Integer.toString(rs.getInt("transactionFee")));             
                phNumberLabel.setText(rs.getString("phone"));            
                reciNameLabel.setText(rs.getString("recipientName"));  
                reciPhoneLabel.setText(rs.getString("recipientPhone")); 
                reciAcctNumber.setText(rs.getString("account"));                     
                reciTxnID.setText(rs.getString("transactionID"));
                datePickLabel.setValue(LocalDate.parse(rs.getObject("date").toString()));            
                timePickLabel.setTime(LocalTime.parse(rs.getObject("time").toString()));            
                bankName.setText(rs.getString("bank"));
            }
            temp = reciTxnID.getText();
           
        }catch (Exception e){
            e.printStackTrace();
        }finally{
                      try{  
                        //conn.close();
                        pS.close();
                        rS.close();
                      }catch(Exception e){
                          
                      }
                }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (txnTypeLabel.getText() == null || txnTypeLabel.getText().length() == 0) {
            errorMessage += "No valid Type(Must be a string)!\n"; 
        }
        if (clientName.getText() == null || clientName.getText().length() == 0) {
            errorMessage += "No valid Name(Must Be a String!)\n"; 
        }
        if (commDeuLabel.getText() == null || commDeuLabel.getText().length() == 0) {
            errorMessage += "No valid Amount!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(commDeuLabel.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Amount (must be an integer)!\n"; 
            }
        }
        if (eFLabel.getText() == null || eFLabel.getText().length() == 0) {
            errorMessage += "No valid Amount!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(eFLabel.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Amount (must be an integer)!\n"; 
            }
        }
        if (txnAmountLabel.getText() == null || txnAmountLabel.getText().length() == 0) {
            errorMessage += "No valid Amount!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(txnAmountLabel.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Amount (must be an integer)!\n"; 
            }
        }
        if (txnFeeLabel.getText() == null || txnFeeLabel.getText().length() == 0) {
            errorMessage += "No valid Fee!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(txnFeeLabel.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Fee (must be an integer)!\n"; 
            }
        }
        if (reciAcctNumber.getText() == null || reciAcctNumber.getText().length() == 0) {
            errorMessage += "No valid Account Number!(must be an integer)\n"; 
        } 
        
        if (reciTxnID.getText() == null || reciTxnID.getText().length() == 0) {
            errorMessage += "No valid Transaction ID!\n"; 
        } 
        if (phNumberLabel.getText() == null || phNumberLabel.getText().length() == 0) {
            errorMessage += "No valid Phone!\n"; 
        }
        if (reciNameLabel.getText() == null || reciNameLabel.getText().length() == 0) {
            errorMessage += "No valid Recipient Name!\n"; 
        }
        if (reciPhoneLabel.getText() == null || reciPhoneLabel.getText().length() == 0) {
            errorMessage += "No valid Recipient Phone!\n"; 
        }
        if (bankName.getText() == null || bankName.getText().length() == 0) {
            errorMessage += "No valid Bank Details!\n"; 
        }
        
        if (datePickLabel.toString() == null || datePickLabel.toString().length() == 0) {
            errorMessage += "No valid Date Of Birth!\n";
        } 
        if (timePickLabel.timeProperty() == null) {
            errorMessage += "No valid Time!\n";
        } 

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}