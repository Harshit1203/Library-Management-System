/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistance.ui.Main;
import javafx.application.Application;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.assistance.database.Databasehandler;

public class MainHandler extends Application{
        @Override
    public void start(Stage primaryStage) {
        try{
       Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }catch(IOException e){
        System.out.println(e);
    }
    
    }
    
     public static void main(String[] args) {
        
         new Thread(()->{
        Databasehandler.getInstance();
         }).start();
         
        launch(args);
    }
}
