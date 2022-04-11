package Controller;

//import Main.JDBC;
import DataAccess.LoginDB;
import Database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable{

    /**  Username field on login form*/
    @FXML private TextField userNameField;

    /**  Password field on login form */
    @FXML private PasswordField passwordField;

    /**Button used to attempt login */
    @FXML private Button loginButton;

    /**displays the locale of the current user*/
    @FXML private Label loginError;

    /**label text for login errors*/
    @FXML private Label localeLabel;

    @FXML private Label userNameLabel;

    @FXML private Label passwordLabel;




    /**
     * attempt to log in when this button is pressed
     * @param event when the login button is clicked
     * @throws IOException throws input output exceptions
     */

    @FXML private void loginClick(ActionEvent event) throws IOException {
       String uName = userNameField.getText();
       String pword = passwordField.getText();

       uName = uName.replaceAll("\\s+", "nope");
       pword = pword.replaceAll("\\s+", "nope");

       Locale currentLocale = Locale.getDefault();

       ResourceBundle errorLang = ResourceBundle.getBundle("LoginScreenBundle", currentLocale);

       //Check if the username or password is empty and print error if so
       if(uName.isEmpty() || pword.isEmpty()){
           loginError.setText(errorLang.getString("badInputKey"));
       }
       //if both username and password are present attempt to validate with database
       else{

           //if username or password are invalid print error indicating so
          try {
              boolean valid = LoginDB.loginCredentials(uName, pword);
              if (LoginDB.loginCredentials(uName, pword) == false) {
                  loginError.setText("Invalid username or password, please try again");
              }

              //if username and password are valid allow user to proceed to next screen
              else {
                  Parent mainMenuParent;
                  mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.FXML")));
                  Scene mainMenuScene = new Scene(mainMenuParent);
                  Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  app_stage.setScene(mainMenuScene);
                  app_stage.show();
              }
          }catch(Exception e){
              loginError.setText(e.getMessage());
          }


       }


    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         *  Get the current user's locale setting and store in a variable
         */
         ZoneId userLocale = ZoneId.systemDefault();
         Locale lang = Locale.getDefault();




         if(userLocale != null) {
             localeLabel.setText(userLocale.toString());
         }

         if(lang != null){
             ResourceBundle language = ResourceBundle.getBundle("LoginScreenBundle", lang);
             userNameLabel.setText(language.getString("unKey"));









         }

    }
}
