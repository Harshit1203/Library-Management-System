/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistance.ui.listMember;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.assistance.database.Databasehandler;
import library.assistance.ui.listbook.ListbookController;

/**
 * FXML Controller class
 *
 * @author Harshit
 */
public class ListmemberController implements Initializable {

      ObservableList<Member> list = FXCollections.observableArrayList(); 
    
    @FXML
    private TableColumn<Member,String> idcol;
    @FXML
    private TableColumn<Member,String> namecol;
    @FXML
    private TableColumn<Member,String> emailcol;
    @FXML
    private TableColumn<Member,String> numcol;

    
    @FXML
    private TableView<Member> tableview;
    @FXML
    private TableColumn<Member,String> sn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initCol();
        loaddata();
    }    
    
     private void initCol()
    {
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idcol.setCellValueFactory(new PropertyValueFactory<>("mem_id")); 
        emailcol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        numcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        sn.setCellValueFactory(new PropertyValueFactory<>("serial"));
    }
     
     
     private void  loaddata()
    {
        Databasehandler handler = Databasehandler.getInstance();
        String qu =" SELECT * FROM MEMBER";
        ResultSet re = handler.execQuery(qu);
        int c =0;
        try{
            while(re.next())
                {
                    ++c;
                  String name = re.getString("name"); 
                  String email = re.getString("email"); 
                  String phone = re.getString("mobile_no"); 
                  String id = re.getString("id"); 
                            
                  list.add(new Member(c,name,id,phone,email));
                }
        }catch(SQLException e){
            System.out.println(e);
        }
        
        tableview.getItems().setAll(list);
    }
     public static class Member{
         
        private final SimpleStringProperty name;
        private final SimpleStringProperty mem_id;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty email;
        private final int serial;
        public Member(int serial,String name,String mem_id,String phone,String email)
        {
            this.serial = serial;
            this.name = new SimpleStringProperty(name);
            this.mem_id = new SimpleStringProperty(mem_id);
            this.phone = new SimpleStringProperty(phone);
            this.email = new SimpleStringProperty(email);
        }

        public int getSerial() {
            return serial;
        }
        
        

        public String getName() {
            return name.get();
        }

        public String getMem_id() {
            return mem_id.get();
        }

        public String getPhone() {
            return phone.get();
        }

        public String getEmail() {
            return email.get();
        }       
     }
}
