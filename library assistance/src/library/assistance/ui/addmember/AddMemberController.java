/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistance.ui.addmember;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.assistance.database.Databasehandler;


public class AddMemberController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private TextField email;
    @FXML
    private TextField mobile;
    @FXML
    private Button savebutton;
    @FXML
    private Button cancelbutton;

     Databasehandler databasehandler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        databasehandler = Databasehandler.getInstance();
    }    

    @FXML
    private void addMember(ActionEvent event) {
         String member_id = id.getText();
        String member_name = name.getText();
        String member_email = email.getText();
        String mob = mobile.getText();
      
        
        if(member_id.isEmpty()||member_name.isEmpty()||member_email.isEmpty()||mob.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please enter all the fields");
            alert.showAndWait();
        }
        else if(!(member_email.contains("@")&&member_email.contains(".com")))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please enter correct email address");
            alert.showAndWait();
        }
        else if(mob.length()!=10)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please enter correct phone number");
            alert.showAndWait();
        }
        else{
        String qu ="INSERT INTO member(id,name,email,mobile_no) values"+"("
                +member_id
                +",'"+member_name+"','"+
                member_email+"','"+mob
                +"');";
                
        System.out.println(qu);
        if(databasehandler.execAction(qu))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success!");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed!");
            alert.showAndWait();
        }
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }
    
}
