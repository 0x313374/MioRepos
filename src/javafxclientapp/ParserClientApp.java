/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxclientapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author MichaelOm
 */
public class ParserClientApp extends Application {
    public static Parent root;


    @Override
    public void start(Stage primaryStage) throws Exception{
        root= FXMLLoader.load(getClass().getResource("parcerClient.fxml"));
        

        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Parser Client JavaFX demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
