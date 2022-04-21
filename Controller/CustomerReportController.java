/**
 * @author Andrew Stowe
 */

package Controller;

import DataAccess.CountriesDB;
import DataAccess.CustomerDB;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerReportController implements Initializable {

    @FXML private ComboBox countryBox;

    @FXML private Label numberLabel;

    @FXML private TableView<Customer> customerTable;

    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> countryColumn;
    @FXML private TableColumn<Customer, String> stateColumn;

    ObservableList<Customer> allCustomers = CustomerDB.getAllCustomers();

    /**
     * This method contains one of my lambda expressions, this lambda expression helps to
     * filter the list by comparing the country selected to the country in the list and returning true
     * if they are the same
     *
     * @param event when the country box is changed filter the list and display in table
     *
     */
    @FXML private void countryBoxChange (ActionEvent event){
        if (countryBox.getSelectionModel().getSelectedItem() != null){
            String country = countryBox.getValue().toString();
            FilteredList<Customer> filteredCustomers = new FilteredList<>(allCustomers);
            filteredCustomers.setPredicate(p -> country.equals(p.getCountry()));

            if(filteredCustomers.isEmpty()==false) {
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                stateColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
                countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

                customerTable.setItems(filteredCustomers);
                numberLabel.setText(String.valueOf(filteredCustomers.size()));
            }
            else{
                customerTable.setItems(null);
                numberLabel.setText("0");
            }
        }
    }

    /**
     * Go to the main menu screen
     * @param event back button click
     * @throws IOException
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
     * Populate the country combo box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = CountriesDB.getAllCountries();
        countryBox.setItems(countries);
    }
}
