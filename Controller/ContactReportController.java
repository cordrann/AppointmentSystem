/**
 * @author Andrew Stowe
 */
package Controller;

import DataAccess.AppointmentDB;
import DataAccess.ContactDB;
import Model.Appointment;
import Model.Contact;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContactReportController implements Initializable {
    /**
     * box to select a contact
     */
    @FXML private ComboBox<Contact> contactBox;
    /**
     * table to show selected contacts appointments
     */
    @FXML private TableView<Appointment> appointmentTable;
    /**
     * appointment id column in appointment table
     */
    @FXML private TableColumn<Appointment, Integer> aidColumn;
    /**
     * title column in appointment table
     */
    @FXML private TableColumn<Appointment, String> titleColumn;
    /**
     * type column in appointment table
     */
    @FXML private TableColumn<Appointment, String> typeColumn;
    /**
     * description column in appointment table
     */
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    /**
     * start column in appointment table
     */
    @FXML private TableColumn<Appointment, LocalTime> startColumn;
    /**
     * end column in appointment table
     */
    @FXML private TableColumn<Appointment, LocalTime> endColumn;
    /**
     * customer id column in appointment table
     */
    @FXML private TableColumn<Appointment, Integer> cidColumn;

    ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();

    /**
     *    This method contains one of my lambda expressions, this lambda expression helps to
     *    filter the list by comparing the contacts ids and returning true if they match
     * @param event If the combo box is changed alter the table to reflect that
     *
     */
    @FXML
    private void contactBoxChange(ActionEvent event){
        if(contactBox.getValue()!= null){
            Contact contact = contactBox.getValue();
            FilteredList<Appointment> filteredAppointments = new FilteredList<>(allAppointments);
            filteredAppointments.setPredicate(p -> contact.getContactID() == p.getContactID());

            if(filteredAppointments.isEmpty()==false) {
                aidColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
                titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
                endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
                cidColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));


                appointmentTable.setItems(filteredAppointments);
            }
            else{appointmentTable.setItems(null);}
        }

    }

    /**
     * go back to the main screen
     * @param event back button is clicked
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
     * populate the combo box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contact> contacts = ContactDB.getAllContacts();
        contactBox.setItems(contacts);
    }
}
