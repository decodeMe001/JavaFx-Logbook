package com.iSoftTech.logbook.view;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.iSoftTech.logbook.model.Transaction;
import com.iSoftTech.logbookDB.DBConnection;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


/**
 *
 * @author Dada abiola
 */
public class LogBookOverviewController implements Initializable {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn;
    @FXML
    private TableColumn<Transaction, String> customersNameColumn;
    @FXML
    private JFXTextField search;
    @FXML
    private Label transactionTypeLabel;
    @FXML
    private Label customersNameLabel;
    @FXML
    private Label commissionDeuLabel;
    @FXML
    private Label eFloatLabel;
    @FXML
    private Label transactionAmountLabel;
    @FXML
    private Label transactionFeeLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label recipientNameLabel;
    @FXML
    private Label recipientPhoneLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label acctNumber;
    @FXML
    private Label txnID;
    @FXML
    private Label bankLabel;
    @FXML
    private Button btnExport;
    @FXML
    private Label dailyTxnLabel;

    @FXML
    private Label transactAmount;
    
 
    private final ObservableList<Transaction> transactionData = FXCollections.observableArrayList();
    Transaction transaction;
    DBConnection conn = new DBConnection();
    private static boolean hasData = false;
    PreparedStatement pS = null;
    ResultSet rS = null;
    private static final String ALPHA_NUM_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String temp;
    private boolean okClicked = false;
    // 1. Wrap the ObservableList in a FilteredList (initially display all data).
    FilteredList<Transaction> filteredData = new FilteredList<>(transactionData, p -> true);
 
    public ObservableList<Transaction> getTransactionData(){
        return transactionData;
    }
    
    public static String randomAplphaNum(int count){
       StringBuilder builder = new StringBuilder();
       while(count-- != 0){
           int character = (int)(Math.random()*ALPHA_NUM_STRING.length());
           builder.append(ALPHA_NUM_STRING.charAt(character));
       }
       return builder.toString();
    }
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        assert transactionTable !=null;
        txnID.setText(randomAplphaNum(5));
        
        // Initialize the transaction table with the two columns.
        transactionTypeColumn.setCellValueFactory(cellData -> cellData.getValue().transactionTypeProperty());
        customersNameColumn.setCellValueFactory(cellData -> cellData.getValue().customersNameProperty());
        loadDataBaseData();
        getTransactionData();
        txnFeeValueCount();
        txnAmountValueCount();
        clearAll();
       
    }
    
    public void loadDataBaseData(){
        String query = "SELECT * FROM buzzDB";
        try{
            pS = conn.Connector().prepareStatement(query);
            rS = pS.executeQuery();
            while(rS.next()){
                transactionData.add(new Transaction(
                            rS.getString("transactionType"),
                            rS.getString("customer"),
                            rS.getDouble("commission"),
                            rS.getDouble("float"),
                            rS.getDouble("transactionAmount"),
                            rS.getInt("transactionFee"),
                            rS.getString("phone"),
                            rS.getString("recipientName"),
                            rS.getString("recipientPhone"),
                            rS.getString("account"),
                            rS.getString("transactionID"),
                            rS.getObject("date"),
                            rS.getObject("time"),
                            rS.getString("bank")
                            
                ));
               transactionTable.setItems(transactionData);
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
    /**
      * 
      * Loading Total Transaction Fee 
      */
     public void txnFeeValueCount(){
        try {
            pS = conn.Connector().prepareStatement("select sum(transactionFee) from buzzDB");
            rS = pS.executeQuery();
            while (rS.next()) {
                dailyTxnLabel.setText(rS.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogBookOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
                      try{  
                        //conn.close();
                        pS.close();
                        rS.close();
                      }catch(Exception e){

                      }
                }
    }
    
     /**
      * 
      * Loading Total Transaction Amount 
      */
     public void txnAmountValueCount(){
        try {
            pS = conn.Connector().prepareStatement("select sum(transactionAmount) from buzzDB");
            rS = pS.executeQuery();
            while (rS.next()) {
                transactAmount.setText(rS.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogBookOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
                      try{  
                        //conn.close();
                        pS.close();
                        rS.close();
                      }catch(Exception e){

                      }
                }
    }
    
    public void setTrxnData(Transaction transaction) {
        
        ResultSet rs;
        String query = "SELECT * FROM buzzDB where transactionID = '"+transaction.getTxnID()+"'";
        try{
            pS = conn.Connector().prepareStatement(query);
            rs = pS.executeQuery();
            while(rs.next()){
                transactionTypeLabel.setText(rs.getString("transactionType"));            
                customersNameLabel.setText(rs.getString("customer"));
                commissionDeuLabel.setText(Double.toString(rs.getDouble("commission")));
                eFloatLabel.setText(Double.toString(rs.getDouble("float")));
                transactionAmountLabel.setText(Double.toString(rs.getDouble("transactionAmount")));
                transactionFeeLabel.setText(Integer.toString(rs.getInt("transactionFee")));
                phoneNumberLabel.setText(rs.getString("phone"));
                recipientNameLabel.setText(rs.getString("recipientName"));
                recipientPhoneLabel.setText(rs.getString("recipientPhone"));
                acctNumber.setText(rs.getString("account"));
                txnID.setText(rs.getString("transactionID"));
                dateLabel.setText(rs.getObject("date").toString());            
                timeLabel.setText(rs.getObject("time").toString()); 
                bankLabel.setText(rs.getString("bank"));
                       
            }
            temp = txnID.getText();
           
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
     @FXML
    void btnRefreshData(ActionEvent event) {
        getTransactionData().clear();
        search.clear();
        
        transactionTable.setItems(transactionData);
        
        // Initialize the transaction table with the two columns.
        transactionTypeColumn.setCellValueFactory(cellData -> cellData.getValue().transactionTypeProperty());
        customersNameColumn.setCellValueFactory(cellData -> cellData.getValue().customersNameProperty());
        loadDataBaseData();
        txnFeeValueCount();
        txnAmountValueCount();
        clearAll();
        
    }
    
    @FXML
    void onTableClicked(MouseEvent event) {
        Transaction trans = (Transaction)transactionTable.getSelectionModel().getSelectedItem();
        ResultSet rS = null;
        String query = "SELECT * FROM buzzDB where transactionID = '"+trans.getTxnID()+"'";
        try{
            pS = conn.Connector().prepareStatement(query);
            rS = pS.executeQuery();
            while(rS.next()){
                transactionTypeLabel.setText(rS.getString("transactionType"));            
                customersNameLabel.setText(rS.getString("customer"));
                commissionDeuLabel.setText(Double.toString(rS.getDouble("commission")));
                eFloatLabel.setText(Double.toString(rS.getDouble("float")));
                transactionAmountLabel.setText(Double.toString(rS.getDouble("transactionAmount")));
                transactionFeeLabel.setText(Integer.toString(rS.getInt("transactionFee")));
                phoneNumberLabel.setText(rS.getString("phone"));
                recipientNameLabel.setText(rS.getString("recipientName"));
                recipientPhoneLabel.setText(rS.getString("recipientPhone"));
                acctNumber.setText(rS.getString("account"));
                txnID.setText(rS.getString("transactionID"));
                dateLabel.setText(rS.getObject("date").toString());            
                timeLabel.setText(rS.getObject("time").toString()); 
                bankLabel.setText(rS.getString("bank"));
                       
            }
            temp = txnID.getText();
           
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
    public void clearAll() {
        transactionTypeLabel.setText(null);
        customersNameLabel.setText(null);
        commissionDeuLabel.setText(null);
        eFloatLabel.setText(null);
        transactionAmountLabel.setText(null);
        transactionFeeLabel.setText(null);
        phoneNumberLabel.setText(null);
        recipientNameLabel.setText(null);
        recipientPhoneLabel.setText(null);
        acctNumber.setText(null);
        txnID.setText(null);
        dateLabel.setText(null);
        timeLabel.setText(null);
        bankLabel.setText(null);
        
    }
    @FXML
    void handleEditTransaction(ActionEvent event) {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
           if (selectedTransaction != null) {
               boolean okClicked = showTransactionEditDialog(selectedTransaction);
             
           } else {
               // Nothing selected.
               Alert alert = new Alert(AlertType.WARNING);
               alert.initOwner(primaryStage);
               alert.setTitle("No Selection");
               alert.setHeaderText("No Transaction Selected");
               alert.setContentText("Please select a transaction on the table.");
                alert.showAndWait();
           }
    }
 
    /**
     * Creates an empty logBook.
     */
    @FXML
    private boolean handleNew(ActionEvent e) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LogBookOverviewController.class.getResource("TransactionEditDialog.fxml"));
            AnchorPane transactionOverview = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            //dialogStage.setTitle("Add Transaction Data");            
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(transactionOverview);
            //scene.setFill(new Color(0, 0, 0, 0));
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setMaximized(false);
            dialogStage.setResizable(false);

            // Set the stock into the controller.
            TransactionEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUpdateDisable();
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
            
        } catch (IOException ex) {
            return false;
        }
         
    }
    @FXML
    boolean handleNewTransaction(ActionEvent event) {        
         try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LogBookOverviewController.class.getResource("TransactionEditDialog.fxml"));
            AnchorPane transactionOverview = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            //dialogStage.setTitle("Add Transaction Data");            
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(transactionOverview);
            scene.setFill(new Color(0, 0, 0, 0));
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setMaximized(false);
            dialogStage.setResizable(false);

            // Set the stock into the controller.
            TransactionEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUpdateDisable();
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
            
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logbook App");
        alert.setHeaderText("About Me");
        alert.setContentText("Developer: Abiola D.\ne-mail: Codedaabiola@gmail.com.\nPhone:+2348080667647.\nSoftware version:1.5");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit(ActionEvent e) {
        System.exit(0);
    }
    /**
    * Opens the txn chart record statistics.
    */
    @FXML
    private void handleTransactionChartRecord(ActionEvent e) {
        showTrxnStatistics();
    }
    @FXML
    private void handlePerformance(ActionEvent event) {
        showPerformanceStart();
    }

    public void showTrxnStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LogBookOverviewController.class.getResource("TransactionChart.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initOwner(primaryStage);
            dialogStage.setTitle("Monthly Transaction Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setMaximized(false);
            dialogStage.setResizable(false);

            // Set the transactions into the controller.
            TransactionChartController controller = loader.getController();
            controller.setTransactionData(transactionData);

            dialogStage.show();

        } catch (IOException e) {
        }
    }
       
       public void showPerformanceStart(){
            try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LogBookOverviewController.class.getResource("IncomeChart.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initOwner(primaryStage);
            dialogStage.setTitle("Monthly Income Transaction Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setMaximized(false);
            dialogStage.setResizable(false);

            // Set the transactions into the controller.
            IncomeChartController controller = loader.getController();
            controller.loadChartStock();

            dialogStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } 
       }
       
    @FXML
    void handleDeleteTransaction(ActionEvent event) {
        Transaction getSelectedRow = transactionTable.getSelectionModel().getSelectedItem();
            if (getSelectedRow != null) {
             try{
                String query = "DELETE FROM buzzDB WHERE transactionID = ?";
                pS = conn.Connector().prepareStatement(query);
                pS.setString(1, getSelectedRow.getTxnID());
                pS.executeUpdate();
                }catch(SQLException e){
                }finally{
                      try{  
                        //conn.close();
                        pS.close();
                        rS.close();
                      }catch(Exception e){
                          
                      }
                }
             okClicked = true;
            if(okClicked){
                NotificationType notificationType = NotificationType.SUCCESS;
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Notice!!!");
                tray.setMessage("Transaction Successfully Deleted.");
                tray.setNotificationType(notificationType);
                tray.showAndDismiss(Duration.millis(3000));
            }
        } else {
               // Nothing selected.
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.initOwner(primaryStage);
               alert.setTitle("No Selection!");
               alert.setHeaderText("No Transaction Selected!!!");
               alert.setContentText("Please select an Item on the table.");
               alert.showAndWait();
           }
        
        getTransactionData().clear();
        loadDataBaseData();
    }
    @FXML
    void ExportAction(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xlsx)", "*.xls");
        chooser.getExtensionFilters().add(filter);
        //FileOutputStream fileOut = new FileOutputStream("SalesDetails.xlsx");//for < 2007 use xls
        // Show save dialog
        File file;
        file = chooser.showSaveDialog(btnExport.getScene().getWindow());
        if (file != null) {
            saveXLSFile(file);

        }
    }
   
    
    private void saveXLSFile(File file) {
        try{
            String query = "select * from buzzDB";
            pS = conn.Connector().prepareStatement(query);
            rS = pS.executeQuery();
            
            //Create Excel Workbook
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook();// For earlier excel version use HSSF
            XSSFSheet sheet = wb.createSheet("LOGBOOK Transaction Report");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("DATE");
            header.createCell(2).setCellValue("TIME");
            header.createCell(3).setCellValue("TRANSACTION TYPE");
            header.createCell(4).setCellValue("CUSTOMER");
            header.createCell(5).setCellValue("COMMISSION");
            header.createCell(6).setCellValue("FLOAT");
            header.createCell(7).setCellValue("AMOUNT");
            header.createCell(8).setCellValue("FEE");
            
            sheet.setZoom(150);//scale 150%
            sheet.autoSizeColumn(0);
            sheet.setColumnWidth(1, 256*25);//256 char lenght
            sheet.autoSizeColumn(2);
            sheet.setColumnWidth(3, 256*25);
            sheet.setColumnWidth(4, 256*25);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            
            int index = 1;
            //fetch database
                while(rS.next()){
                    XSSFRow row = sheet.createRow(index);
                    row.createCell(0).setCellValue(rS.getInt("id"));
                    row.createCell(1).setCellValue(rS.getString("date"));
                    row.createCell(2).setCellValue(rS.getString("TIME"));
                    row.createCell(3).setCellValue(rS.getString("transactionType"));
                    row.createCell(4).setCellValue(rS.getString("customer"));
                    row.createCell(5).setCellValue(Double.toString(rS.getDouble("commission")));
                    row.createCell(6).setCellValue(Double.toString(rS.getDouble("float")));
                    row.createCell(7).setCellValue(Double.toString(rS.getDouble("transactionAmount")));
                    row.createCell(8).setCellValue(Integer.toString(rS.getInt("transactionFee")));
                    index++;
                }
                
                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
                
                okClicked = true;
                            if(okClicked){
                                NotificationType notificationType = NotificationType.SUCCESS;
                                TrayNotification tray = new TrayNotification();
                                tray.setTitle("Excel Sheet Created!!!");
                                tray.setMessage("Financial Details Successfully Created.");
                                tray.setNotificationType(notificationType);
                                tray.showAndDismiss(Duration.millis(3000));
                            }

                }catch (SQLException e){
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LogBookOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LogBookOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                      try{  
                        //conn.close();
                        pS.close();
                        rS.close();
                      }catch(Exception e){
                          
                      }
                }
    }
     
    public boolean showTransactionEditDialog(Transaction transaction) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LogBookOverviewController.class.getResource("TransactionEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            //dialogStage.setTitle("Edit Transaction Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.setFill(new Color(0, 0, 0, 0));
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setMaximized(false);
            dialogStage.setResizable(false);

            // Set the transaction into the controller.
            TransactionEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTransactionData(transaction);
            controller.setSaveDisable();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    void searchBox(KeyEvent event) {
        search.textProperty().addListener((observableValue,oldValue,newValue)->{
            filteredData.setPredicate((Predicate<? super Transaction>)trans->{
                if(newValue==null||newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(trans.getTransactionType().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(trans.getCustomersName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
                
            });
        });
        SortedList<Transaction> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(transactionTable.comparatorProperty());
        transactionTable.setItems(sortedData);
    }
}
