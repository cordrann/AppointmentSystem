package Controller;

//import Main.JDBC;
import DataAccess.LoginDB;
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

import java.io.*;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    @FXML private Label zoneLabel;

    @FXML private Label userNameLabel;

    @FXML private Label passwordLabel;






    private void printToFile(String output) throws IOException {
        File outputFile = new File("../AndrewStoweAppointmentSystem/login_activity.txt");
        PrintWriter loginWriter = new PrintWriter(new FileWriter(outputFile, true));
        loginWriter.println(output);
        loginWriter.close();
    }

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





       ResourceBundle errorLang = ResourceBundle.getBundle("Controller/LoginScreenBundle", currentLocale);

       //Check if the username or password is empty and print error if so
       if(uName.isEmpty() || pword.isEmpty()){
           loginError.setText(errorLang.getString("badInputKey"));
           if(uName.isEmpty() == false){

                printToFile(uName + " attempted and failed to login without a password at " +
                        ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")) + "\n");

           }
           else{
               printToFile("Unknown user attempted and failed to login without a username at " +
                       ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")) + "\n");


           }

       }
       //if both username and password are present attempt to validate with database
       else{

           //if username or password are invalid print error indicating so
          try {
              if (LoginDB.loginCredentials(uName, pword) == false) {
                  loginError.setText(errorLang.getString("falseLoginKey"));
                  printToFile(uName+" failed to login at " +
                          ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")) + "\n");
              }

              //if username and password are valid allow user to proceed to next screen
              else {

                  printToFile(uName+" successfully logged in at " +
                          ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")) + "\n");

                  Integer userID = LoginDB.getUserID(uName, pword);


                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.FXML"));
                  Parent mainMenuParent = loader.load();

                  MainMenuController mmc = loader.getController();
                  mmc.transferUserData(uName, userID);

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
         ZoneId userZone = ZoneId.systemDefault();
         Locale lang = Locale.getDefault();

         zoneLabel.setText(userZone.toString());

         ResourceBundle language = ResourceBundle.getBundle("Controller/LoginScreenBundle", lang);
         userNameLabel.setText(language.getString("unKey"));
         passwordLabel.setText(language.getString("pwKey"));
         loginButton.setText(language.getString("loginKey"));


    }
}
