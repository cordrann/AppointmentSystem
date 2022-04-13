package Controller;

import DataAccess.CustomerDB;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerMenuController implements Initializable {
    /**
     * automatically generate field for display purposes only
     */
    @FXML private TextField cidField;

    /**
     * new/selected customers full name
     */
    @FXML private TextField customerNameField;

    /**
     * new/selected customers address
     */
    @FXML private TextField addressField;

    /**
     * new/selected customers postal/zip code
     */
    @FXML private TextField postalField;

    /**
     * new/selected customers phone number
     */
    @FXML private TextField phoneField;

    /**
     * new/selected customers country
     */


    @FXML private ComboBox countryBox;

    /**
     * new/selected customers state(filtered by selected country)
     */
    @FXML private ComboBox stateBox;

    /**
     * buttons to add/update/delete customers or return to main menu
     */
    @FXML private Button addCustomerButton;
    @FXML private Button updateCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button backButton;

    @FXML private Label errorLabel;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> cidColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, String> postalColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, String> countryColumn;
    @FXML private TableColumn<Customer, String> stateColumn;

    /**
     * add customer to the database after checking for valid input
     *
     * @param event add button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void addButtonClick(ActionEvent event) throws IOException {
        //try/catch block makes sure data types are appropriate

        try {
            //parse user input and put into variables
            String newName = customerNameField.getText();
            String newAddress = addressField.getText();
            String newCountry = countryBox.getValue().toString();
            String newPostal = postalField.getText();
            String newPhone = phoneField.getText();
            String newState = stateBox.getValue().toString();


            Customer newCustomer = new Customer(1, newName, newAddress, newPostal, newPhone, "a", "a");
        } catch (Exception e) {
            errorLabel.setText("Invalid input, try again.");
        }

    }

    /**
     * update customer in the database after checking for valid input
     *
     * @param event update button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void updateButtonClick(ActionEvent event) throws IOException {

    }

    /**
     * delete a customer from the database if one is selected
     * after deleting all related appointments
     *
     * @param event delete button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void deleteButtonClick(ActionEvent event) throws IOException {

    }

    /**
     * go back to the main menu screen
     *
     * @param event back button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void backButtonClick(ActionEvent event) throws IOException {
        Parent customerMenuParent;
        customerMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.FXML")));
        Scene customerMenuScene = new Scene(customerMenuParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(customerMenuScene);
        app_stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customers = CustomerDB.getAllCustomers();

        if(customers.isEmpty()==false) {
           cidColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
           nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
           addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
           postalColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
           phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
           countryColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
           stateColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

           customerTable.setItems(customers);
        }

    }
}
