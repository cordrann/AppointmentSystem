package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CustomerMenuController {
    /** automatically generate field for display purposes only*/
    @FXML private TextField cidField;

    /** new/selected customers full name */
    @FXML private TextField customerNameField;

    /** new/selected customers address*/
    @FXML private TextField addressField;

    /** new/selected customers postal/zip code */
    @FXML private TextField postalField;

    /** new/selected customers phone number */
    @FXML private TextField phoneField;

    /** new/selected customers country*/
    @FXML private ComboBox countryBox;

    /** new/selected customers state(filtered by selected country)*/
    @FXML private ComboBox stateBox;

    /** buttons to add/update/delete customers or return to main menu*/
    @FXML private Button addCustomerButton;
    @FXML private Button updateCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button backButton;

    /**
     * add customer to the database after checking for valid input
     * @param event add button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML private void addButtonClick(ActionEvent event) throws IOException{

    }

    /**
     * update customer in the database after checking for valid input
     * @param event update button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML private void updateButtonClick(ActionEvent event) throws IOException{

    }

    /**
     * delete a customer from the database if one is selected
     * after deleting all related appointments
     * @param event delete button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML private void deleteButtonClick(ActionEvent event) throws IOException{

    }

    /**
     * go back to the main menu screen
     * @param event back button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML private void backButtonClick(ActionEvent event) throws IOException {
        Parent customerMenuParent;
        customerMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.FXML")));
        Scene customerMenuScene = new Scene(customerMenuParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(customerMenuScene);
        app_stage.show();

    }

}
