/**
 * @author Andrew Stowe
 */
package Controller;

import DataAccess.CountriesDB;
import DataAccess.CustomerDB;
import DataAccess.FirstLevelDivisionsDB;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class CustomerMenuController implements Initializable {
    /**
     * automatically generated customer id, field for display purposes only
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
     * button to save customers
     */
    @FXML private Button saveCustomerButton;
    /**
     * button to load customer data into input fields
     */
    @FXML private Button updateCustomerButton;
    /**
     * button to delete a customer
     */
    @FXML private Button deleteCustomerButton;
    /**
     * button to go back to the main screen
     */
    @FXML private Button backButton;
    /**
     * button to clear the screen
     */
    @FXML private Button clearButton;
    /**
     * label to show error messages
     */
    @FXML private Label errorLabel;
    /**
     * table of customers
     */
    @FXML private TableView<Customer> customerTable;
    /**
     * customer id column in customer table
     */
    @FXML private TableColumn<Customer, Integer> cidColumn;
    /**
     * name column in customer table
     */
    @FXML private TableColumn<Customer, String> nameColumn;
    /**
     * street address column in customer table
     */
    @FXML private TableColumn<Customer, String> addressColumn;
    /**
     * postal column in customer table
     */
    @FXML private TableColumn<Customer, String> postalColumn;
    /**
     * phone column in customer table
     */
    @FXML private TableColumn<Customer, String> phoneColumn;
    /**
     * country column in customer table
     */
    @FXML private TableColumn<Customer, String> countryColumn;
    /**
     * state column in customer table
     */
    @FXML private TableColumn<Customer, String> stateColumn;

    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * add customer to the database after checking for valid input
     *
     * @param event add button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void saveButtonClick(ActionEvent event) throws IOException {
        //if the customer id is blank try to insert, if populated update
        if(cidField.getText() == "") {
            //check for input in text fields
            if(customerNameField.getText() == "" || addressField.getText()== "" || postalField.getText() == "" || phoneField.getText() == "") {
                errorLabel.setText("Missing input value, please try again");
            }
            else {
                try {
                    //parse user input and put into variables then insert the customer
                    String newName = customerNameField.getText();
                    String newAddress = addressField.getText();
                    String newPostal = postalField.getText();
                    String newPhone = phoneField.getText();
                    String newState = stateBox.getValue().toString();


                    CustomerDB.insertCustomer(newName, newAddress, newPostal, newPhone, newState);

                    tablePopulate();
                    errorLabel.setText("");

                } catch (Exception e) {
                    errorLabel.setText("Invalid input, try again.");
                }
            }



        }
        //id field was populated so update
        else{
            //make sure text fields aren't empty
            if(customerNameField.getText() == "" || addressField.getText()== "" || postalField.getText() == "" || phoneField.getText() == "") {
                errorLabel.setText("Missing input value, please try again");
            }
            else{
                try{
                    Integer cid = parseInt(cidField.getText());
                    String newName = customerNameField.getText();
                    String newAddress = addressField.getText();
                    String newPostal = postalField.getText();
                    String newPhone = phoneField.getText();
                    String newState = stateBox.getValue().toString();

                    CustomerDB.updateCustomer(cid, newName,newAddress, newPostal, newPhone, newState);

                    tablePopulate();
                    errorLabel.setText("Customer with ID "+cid+" updated");

                }catch (Exception e){
                    errorLabel.setText("Invalid input, try again");
                }
            }
        }
    }

    /**
     * clear the input fields when the clear button is clicked
     * @param event
     */
   @FXML private void clearButtonClicked(ActionEvent event) {
        clear();
   }



    /**
     * put selected customers data into input fields
     *
     * @param event update button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void updateButtonClick(ActionEvent event) throws IOException {
        try{
            Customer customer = customerTable.getSelectionModel().getSelectedItem();

            cidField.setText(customer.getCustomerID().toString());
            customerNameField.setText(customer.getCustomerName());
            addressField.setText(customer.getAddress());
            phoneField.setText(customer.getPhone());
            postalField.setText(customer.getAddress());
            countryBox.getSelectionModel().select(customer.getCountry());
            stateBox.getSelectionModel().select(customer.getDivision());

        }catch(Exception e){
            errorLabel.setText("");
        }

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
        Customer selectedCustomer = null;
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        //make sure customer is selected
        if(selectedCustomer == null){
            errorLabel.setText("Please select a customer before trying to delete");
        }
        //show warning and then delete customer if confirmed
        else{
            confirmation.setHeaderText("Are you sure you would like to delete this customer?");
            confirmation.setContentText(selectedCustomer.getCustomerName());
            Optional<ButtonType> option = confirmation.showAndWait();
            if(option.get() == null){
                errorLabel.setText("Please select a button to proceed");
            }
            else if (option.get() ==ButtonType.OK){
                CustomerDB.deleteCustomer(selectedCustomer.getCustomerID());
                errorLabel.setText("Customer "+selectedCustomer.getCustomerName()+" deleted");
                clear();
                tablePopulate();
            }
            else{
                errorLabel.setText("Customer deletion canceled");
            }

        }
    }

    /**
     * Clears all input fields
     */
    private void clear() {
        try{
            cidField.setText("");
            customerNameField.setText("");
            addressField.setText("");
            postalField.setText("");
            phoneField.setText("");

            stateBox.getSelectionModel().clearSelection();
            stateBox.setValue(null);
            countryBox.getSelectionModel().clearSelection();
            countryBox.setValue(null);


        }catch(Exception e){
            errorLabel.setText("Clear failed");
        }
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

    /**
     *
     * @param event When a country is selected populate the state box
     * @throws IOException
     */
    @FXML private void countrySelection(ActionEvent event) throws IOException {
        if(countryBox.getValue() != null) {
            String country = countryBox.getValue().toString();
            ObservableList<String> divisions = FirstLevelDivisionsDB.getFilteredDivisions(country);
            stateBox.setItems(divisions);
        }
    }

    /**
     * populates the table view with all customers
     */
    private void tablePopulate(){
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

    /**
     * sets table to initial values and populates country box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablePopulate();

        ObservableList<String> countries = CountriesDB.getAllCountries();
        countryBox.setItems(countries);


    }
}
