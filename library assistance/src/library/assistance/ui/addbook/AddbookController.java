
package library.assistance.ui.addbook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.assistance.database.Databasehandler;


public class AddbookController implements Initializable {
    
   
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField author;
    @FXML
    private TextField isbn;
    @FXML
    private TextField publisher;
    @FXML
    private TextField price;
    private TextField quantity;
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
    private void addbook(ActionEvent event) {
        
         String bookid = id.getText();
        String bookname = name.getText();
        String authorname = author.getText();
        String publishername = publisher.getText();
        String bookprice = price.getText();
        
        String ISBN =isbn.getText();
        
        if(bookid.isEmpty()||bookname.isEmpty()||bookprice.isEmpty()||ISBN.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please enter all the fields");
            alert.showAndWait();
        }
        String qu ="INSERT INTO BOOKS(title,ISBN,author,publisher,price,isavail,id) values"+"("
                +"'"+bookname+"',"+
                ISBN+","+
                "'"+authorname+"',"+
                "'"+publishername+"',"+
                bookprice+","+
                true+","+bookid+");";
                
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

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }
    
}
