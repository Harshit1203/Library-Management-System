/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistance.ui.settings;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harshit
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField daysField;
    @FXML
    private TextField amontfield;
    @FXML
    private TextField usernameFiled;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initDefaultValues();
    }

    @FXML
    private void saveAction(ActionEvent event) {

        int days = Integer.parseInt(daysField.getText());
        int fine = Integer.parseInt(this.amontfield.getText());
        String user = this.usernameFiled.getText();
        String pass = this.passwordfield.getText();

        Preferrences pref = Preferrences.loadPreferences();
        pref.setFineAmount(fine);
        pref.setMinwithoutFine(days);
        pref.setUsername(user);
        pref.setPassword(pass);

        Preferrences.writeConfig(pref);
        
         Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void cancelAction(ActionEvent event) {
         Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void initDefaultValues() {
        Preferrences pref = Preferrences.loadPreferences();
        daysField.setText(String.valueOf(pref.getMinwithoutFine()));
        amontfield.setText(String.valueOf(pref.getFineAmount()));
        usernameFiled.setText(pref.getUsername());
        passwordfield.setText(pref.getPassword());
    }

}
