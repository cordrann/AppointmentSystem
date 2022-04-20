package Controller;

import DataAccess.AppointmentDB;
import DataAccess.ContactDB;
import DataAccess.CustomerDB;
import DataAccess.UserDB;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    @FXML private DatePicker startDatePicker;
    @FXML private RadioButton weekButton;
    @FXML private RadioButton monthButton;

    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> aidColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> locationColumn;
    @FXML private TableColumn<Appointment, String> contactColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, LocalTime> startColumn;
    @FXML private TableColumn<Appointment, LocalTime> endColumn;
    @FXML private TableColumn<Appointment, Integer> cidColumn;
    @FXML private TableColumn<Appointment, Integer> uidColumn;

    @FXML
    private void backButtonClick(ActionEvent event) throws IOException {
        Parent customerMenuParent;
        customerMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.FXML")));
        Scene customerMenuScene = new Scene(customerMenuParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(customerMenuScene);
        app_stage.show();

    }

    private void tablePopulate(ObservableList<Appointment> appointments){

        if(appointments.isEmpty()==false) {
            aidColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            cidColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            uidColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<> ("contactID"));



            appointmentTable.setItems(appointments);
        }

        else{appointmentTable.setItems(null);}

    }

    @FXML private void datePickedOrRadioToggle (ActionEvent event){
        if(startDatePicker.getValue() != null){
            ObservableList<Appointment> appointments = AppointmentDB.getTimeAppointments(startDatePicker.getValue(), weekButton.isSelected());

            tablePopulate(appointments);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> appointments = AppointmentDB.getTimeAppointments(LocalDate.now(), weekButton.isSelected());

        tablePopulate(appointments);


    }
}
