
package library.assistance.ui.listMember;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListMemberHandler extends Application {
      @Override
    public void start(Stage primaryStage) {
        try{
       Parent root = FXMLLoader.load(getClass().getResource("listmember.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }catch(IOException e){
        System.out.println(e);
    }
    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
}
