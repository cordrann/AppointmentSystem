package View;

import Main.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable{

    //login fields
    @FXML private TextField userNameField;
    @FXML private PasswordField passwordField;

    //login button
    @FXML private Button loginButton;

    //login messages
    @FXML private Label loginError;
    @FXML private Label locale;


    @FXML private void loginClick(ActionEvent event) throws IOException {
       String uName = userNameField.getText();
       String pword = passwordField.getText();

       if(uName.isEmpty() || pword.isEmpty()){
           loginError.setText("Please enter both a username and password to continue");
       }
       else{
           JDBC.validate

       }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
