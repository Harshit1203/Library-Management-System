/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistance.ui.Main;

import com.jfoenix.effects.JFXDepthManager;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.assistance.database.Databasehandler;

/**
 * FXML Controller class
 *
 * @author Harshit
 */
public class MainController implements Initializable {

    @FXML
    private HBox book_info;
    @FXML
    private HBox member_info;
    @FXML
    private TextField bookIDinput;
    @FXML
    private Text bookname;
    @FXML
    private Text bookauthor;
    @FXML
    private Text bookstatus;
    Databasehandler handler;
    @FXML
    private TextField memberIdInput;
    @FXML
    private Text memberName;
    @FXML
    private Text MemberContact;

    @FXML
    private VBox buttonoptions;
    @FXML
    private TextField iBookId;
    @FXML
    private ListView<String> issudatalist;
    /**
     * Initializes the controller class.
     */

    private boolean isReadyForSubmission = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        handler = Databasehandler.getInstance();
        JFXDepthManager.setDepth(book_info, 3);
        JFXDepthManager.setDepth(member_info, 3);
        JFXDepthManager.setDepth(buttonoptions, 3);
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/library/assistance/ui/addbook/addbook.fxml", "ADD NEW BOOKS");
    }

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/library/assistance/ui/addmember/addMember.fxml", "ADD NEW MEMBER");
    }

    @FXML
    private void loadListBooks(ActionEvent event) {
        loadWindow("/library/assistance/ui/listbook/listbook.fxml", "SHOW BOOKS");
    }

    @FXML
    private void loadListMember(ActionEvent event) {
        loadWindow("/library/assistance/ui/listMember/listmember.fxml", "SHOW MEMBER");
    }

    void loadWindow(String loc, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void clearBookCase() {
        bookname.setText("");
        bookauthor.setText("");
        bookstatus.setText("");
    }

    void clearMemberCase() {
        memberName.setText("");
        MemberContact.setText("");
    }

    @FXML
    private void loadbookinfo(ActionEvent event) {
        String bookid = bookIDinput.getText();
        String qu = "SELECT * FROM BOOKS WHERE id =" + bookid + ";";
        ResultSet rs = handler.execQuery(qu);
        boolean flag = false;
        try {
            while (rs.next()) {
                String bname = rs.getString("title");
                String bauthor = rs.getString("author");

                boolean bstatus = rs.getBoolean("isavail");
                flag = true;
                bookname.setText(bname);
                bookauthor.setText(bauthor);

                String status = (bstatus) ? "Available" : "Unavailable";
                bookstatus.setText(status);
            }

            if (!flag) {
                clearBookCase();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("No such Book in the Library!");
                alert.showAndWait();
            }
        } catch (Exception e) {
        }

    }

    @FXML
    private void loadMemberinfo(ActionEvent event) {
        String mem_id = memberIdInput.getText();
        String qu = "SELECT * FROM MEMBER WHERE id =" + mem_id + ";";
        ResultSet rs = handler.execQuery(qu);
        boolean flag = false;
        try {
            while (rs.next()) {
                String mname = rs.getString("name");
                String memail = rs.getString("email");
                String mPhone_no = rs.getString("mobile_no");
                flag = true;
                memberName.setText(mname);
                MemberContact.setText(memail + "\n" + mPhone_no);
            }

            if (!flag) {
                clearMemberCase();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("No such Member in the system!");
                alert.showAndWait();
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void loadIssueOperation(ActionEvent event) {
        String mem_id = memberIdInput.getText();
        String bookid = bookIDinput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm issue Operations");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to issue  the book " + bookname.getText() + " to " + memberName.getText());
        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            String act = "INSERT into ISSUE(bookID,memberID,issueDate) values (" + bookid + "," + mem_id + "," + "NOW());";
            String act3 = "UPDATE BOOKS SET isavail =false WHERE id =" + bookid + ";";

            if (handler.execAction(act) && handler.execAction(act3)) {

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success");
                alert2.setHeaderText(null);
                alert2.setContentText("Book issued");
                alert2.showAndWait();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Failed");
                alert2.setHeaderText(null);
                alert2.setContentText("Book not issued");
                alert2.showAndWait();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Cancelled");
            alert2.setHeaderText(null);
            alert2.setContentText("issue operation cancelled!");
            alert2.showAndWait();
        }
    }

    @FXML
    private void loadissueBookInfo(ActionEvent event) {
        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmission = false;
        String bookid = iBookId.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookID = " + bookid;
        ResultSet rs = handler.execQuery(qu);

        try {
            while (rs.next()) {
                String ibookId = bookid;
                String imemberId = rs.getString("memberID");
                Timestamp issuedate = rs.getTimestamp("issueDate");
                String rc = rs.getString("renew_count");

                issueData.add("Issue date and time: " + issuedate.toString());
                issueData.add("Renew count: " + rc);

                String qu2 = "select * from BOOKS where id = " + ibookId;
                String qu3 = "SELECT * FROM member WHERE id = " + imemberId;
                ResultSet rs1 = handler.execQuery(qu2);
                while (rs1.next()) {
                    issueData.add("**************BOOK INFORMATION*****************");
                    issueData.add("Book name: " + rs1.getString("title"));
                    issueData.add("Book ID: " + rs1.getString("id"));
                    issueData.add("ISBN: " + rs1.getString("ISBN"));
                    issueData.add("Price: " + rs1.getString("price"));
                }

                ResultSet rs2 = handler.execQuery(qu3);
                while (rs2.next()) {
                    issueData.add("****************MEMBER INFORMATION****************");
                    issueData.add("Member name: " + rs2.getString("name"));
                    issueData.add("Member id: " + rs2.getString("id"));
                    issueData.add("Phone no.:  " + rs2.getString("mobile_no"));
                    issueData.add("email: " + rs2.getString("email"));
                }
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                Date d1 = new SimpleDateFormat("yyyy-M-dd").parse(issuedate.toString());
                Date d2 = new SimpleDateFormat("yyyy-M-dd").parse(now.toString());

                long diff = (d2.getTime() - d1.getTime()) / (1000 * 24 * 60 * 60);
                
                double penalty = 0;

                if (diff > 14) {
                    penalty = (diff - 14) * 10;
                }
                issueData.add("\n\n Penalty: " + penalty);
                isReadyForSubmission = true;

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        issudatalist.getItems().setAll(issueData);
    }

    @FXML
    private void bookSubmissionOp(ActionEvent event) {

        if (!isReadyForSubmission) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("WRONG INPUT");
            alert2.setHeaderText(null);
            alert2.setContentText("Please enter valid Book ID!");
            alert2.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Submission");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to submit the book");
        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            String id = iBookId.getText();

            String ac = "DELETE FROM ISSUE WHERE bookID = " + id;
            String ac2 = "update BOOKS SET isavail = true WHERE id = " + id;

            try {
                if (handler.execAction(ac) && handler.execAction(ac2)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Submitted");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Book has been returned");
                    alert2.showAndWait();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("FAILED");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Book is not Submitted");
                    alert2.showAndWait();
                }
            } catch (Exception e) {
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Operation Cancelled");
            alert2.setHeaderText(null);
            alert2.setContentText("Book not submitted");
            alert2.showAndWait();
        }

    }

    @FXML
    private void loadRenewOp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Renerw Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to renew the book");
        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            String id = iBookId.getText();
            String ac2 = "update issue SET issueDate = NOW(),renew_count = renew_count+1 WHERE bookID = " + id + ";";

            try {
                if (handler.execAction(ac2)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("RENEW OPERATION");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Book has been renewed");
                    alert2.showAndWait();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("FAILED");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Book can not be renewed");
                    alert2.showAndWait();
                }
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void loadSettings(ActionEvent event) {
        
        loadWindow("/library/assistance/ui/settings/settings.fxml", "SETTINGS");
    }
}
