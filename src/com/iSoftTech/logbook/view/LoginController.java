package com.iSoftTech.logbook.view;

import com.iSoftTech.logbook.MainApp;
import com.iSoftTech.logbookDB.DBModel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Dada abiola
 */
public class LoginController implements Initializable{
    
   // Reference to the main application.
    public MainApp mainApp;
    public DBModel loginModel = new DBModel();
    
    @FXML
    private JFXPasswordField handlePass;
    @FXML
    private JFXTextField handleUser;
    @FXML
    private Hyperlink createAccount;
    
    @FXML
    private Label isConnected;
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
           isConnected.setText("Connection Started!");
           DBModel insert = new DBModel();
        try {
            insert.insertUser();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void btnCloseOnAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    private Button btnClose;
    
    @FXML
    public void onLogin(ActionEvent event) throws IOException{
        if(isAllFieldFillup()){
            try{
                 if(loginModel.adminLogin(handleUser.getText(), handlePass.getText())){

                     ((Node)event.getSource()).getScene().getWindow().hide();
                     Stage primaryStage = new Stage();
                     FXMLLoader loader = new FXMLLoader();
                     loader.setLocation(LoginController.class.getResource("LogBookOverview.fxml"));
                     AnchorPane root = (AnchorPane) loader.load();
                     Scene scene = new Scene(root);
                     primaryStage.setScene(scene);
                     primaryStage.setTitle("LogBook App");
                     primaryStage.getIcons().add(new Image("resources/images/Briefcase.png"));
                     primaryStage.setResizable(true);
                     primaryStage.setMaximized(false);
                     primaryStage.show();

                 }else{
                     isConnected.setText("Access Denied! Invalid username Or password");
                     handleUser.clear();
                     handlePass.clear();
                 }
                 }catch(Exception ex){
                     System.out.println(ex.getMessage());
                 }
        }
    }
    
    private boolean isAllFieldFillup(){
        boolean fillup;
        if(handleUser.getText() == null ||handlePass.getText() == null){

            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Error 504!!!");
            tray.setMessage("Username or Password should not be Empty!");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));

            fillup = false;
        }
        else fillup = true;
        return fillup;
    }
}
