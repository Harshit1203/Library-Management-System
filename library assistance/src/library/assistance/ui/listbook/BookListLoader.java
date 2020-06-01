
package library.assistance.ui.listbook;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.assistance.database.Databasehandler;

public class BookListLoader extends Application{
    
    
      @Override
    public void start(Stage primaryStage) {
       try{
          Parent root = FXMLLoader.load(getClass().getResource("listbook.fxml"));
        
          Scene scene = new Scene(root);
        
          primaryStage.setScene(scene);
           primaryStage.show();
         }
        catch(IOException e){
           System.out.println(e);
    }
    }
    
    
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
