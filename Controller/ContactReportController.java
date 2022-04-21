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
    @FXML private ComboBox<Contact> contactBox;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> aidColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, LocalTime> startColumn;
    @FXML private TableColumn<Appointment, LocalTime> endColumn;
    @FXML private TableColumn<Appointment, Integer> cidColumn;

    ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();

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
        ObservableList<Contact> contacts = ContactDB.getAllContacts();
        contactBox.setItems(contacts);
    }
}
