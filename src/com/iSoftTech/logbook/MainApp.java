package com.iSoftTech.logbook;

import com.iSoftTech.logbookDB.DBModel;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;


/**
 *
 * @author Dada abiola
 */
public class MainApp extends Application {
    
    private static Stage primaryStage;

       
    /**
     * Constructor
     */
    public MainApp() throws SQLException {
        
        DBModel model = new DBModel();
        model.createNewDatabase();
      
    
    }

    @Override
    public void start(Stage primaryStage) {
       try{
            Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            scene.setFill(new Color(0, 0, 0, 0));
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.getIcons().add(new Image("resources/images/Briefcase.png"));
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.setMinHeight(500.0);
            primaryStage.setMinWidth(800.0);
            primaryStage.show();
            
        } catch(Exception e){
        }

    }
    
     /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
        
}
