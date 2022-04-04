package Controller;

//import Main.JDBC;
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




    /**
     * attempt to log in when this button is pressed
     * @param event when the login button is clicked
     * @throws IOException throws input output exceptions
     */

    @FXML private void loginClick(ActionEvent event) throws IOException {
       String uName = userNameField.getText();
       String pword = passwordField.getText();

       //Check if the username or password is empty and print error if so
       if(uName.isEmpty() || pword.isEmpty()){
           loginError.setText("Please enter both a username and password to continue");
       }
       //if both username and password are present attempt to validate with database
       else{
           //JDBC.validate

           //if username or password are invalid print error indicating so
           if(false == true){
               loginError.setText("Invalid username or password, please try again");
           }

           //if username and password are valid allow user to proceed to next screen
           else{
               Parent mainMenuParent;
               mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.FXML")));
               Scene mainMenuScene = new Scene(mainMenuParent);
               Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               app_stage.setScene(mainMenuScene);
               app_stage.show();
           }


       }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         *  Get the current user's locale setting and store in a variable
         */
        // Locale userLocale = Locale.getDefault();
         //localeLabel.setText(userLocale.toString());

    }
}
