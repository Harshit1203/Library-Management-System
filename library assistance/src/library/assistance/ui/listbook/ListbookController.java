/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistance.ui.listbook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import library.assistance.database.Databasehandler;

/**
 * FXML Controller class
 *
 * @author Harshit
 */
public class ListbookController implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList(); 
    
    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Book> tableview;
    @FXML
    private TableColumn<Book, String> availability;
    @FXML
    private TableColumn<Book, String> titlecol;
    @FXML
    private TableColumn<Book, String> idcol;
    @FXML
    private TableColumn<Book, String> authorcol;
    @FXML
    private TableColumn<Book, String> publishercol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loaddata();
        
    }  
    
    private void initCol()
    {
        titlecol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idcol.setCellValueFactory(new PropertyValueFactory<>("bookid")); 
        publishercol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        authorcol.setCellValueFactory(new PropertyValueFactory<>("author"));
        availability.setCellValueFactory(new PropertyValueFactory<>("isavail"));
    }
    
    private void  loaddata()
    {
        Databasehandler handler = Databasehandler.getInstance();
        String qu =" SELECT * FROM BOOKS";
        ResultSet re = handler.execQuery(qu);
        
        try{
            while(re.next())
                {
                  String name = re.getString("title"); 
                  String author = re.getString("author"); 
                  String publisher = re.getString("publisher"); 
                  String id = re.getString("id"); 
                  Boolean avail = re.getBoolean("isavail"); 
                  
                  list.add(new Book(name,publisher,author,id,avail));
                }
        }catch(SQLException e){
            System.out.println(e);
        }
        
        tableview.getItems().setAll(list);
    }
    
   public static class Book{
        private final SimpleStringProperty title;
        private final SimpleStringProperty publisher;
        private final SimpleStringProperty bookid;
        private final SimpleStringProperty author;
        private final SimpleStringProperty isavail;
        
        Book(String title,String publisher,String author,String bookid,boolean isavail)
        {
            this.title = new SimpleStringProperty(title);
            this.publisher = new SimpleStringProperty(publisher);
            this.author = new SimpleStringProperty(author);
            this.bookid = new SimpleStringProperty(bookid);
            if(isavail)
               this.isavail = new SimpleStringProperty("Available");
            else
               this.isavail = new SimpleStringProperty("Not Available"); 
        } 

        public String getTitle() {
            return title.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public String getBookid() {
            return bookid.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getIsavail() {
            return isavail.get();
        }
        
        
             
    }
    
}
