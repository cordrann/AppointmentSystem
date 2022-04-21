/**
 * @author Andrew Stowe
 */


package Controller;

import DataAccess.*;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
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
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class AppointmentMenuController implements Initializable{

    //auto generated appointment id
    @FXML private TextField aidField;

    /**
     * Field to input appointment title
     */
    @FXML private TextField titleField;

    /**
     * Field to input appointment description
     */
    @FXML private TextField descField;

    /**
     * Field to input appointment location
     */
    @FXML private TextField locationField;

    @FXML private  ComboBox<Customer> customerBox;

    @FXML private ComboBox<User> userBox;


    @FXML private ComboBox<Contact> contactBox;


    @FXML private ComboBox typeBox;

    @FXML private DatePicker datePicker;

    @FXML private ComboBox<String> startBox;
    @FXML private ComboBox<String> endBox;


    @FXML private Button saveButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private Button clearButton;

    @FXML private Label errorLabel;

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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm   MM/dd/yyyy  VV");

    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * add/update appointment to the database after checking for valid input
     *
     * @param event save button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void saveButtonClick(ActionEvent event) throws IOException {
        //check if aid is populated to determine if new or update
        if (aidField.getText() == "") {
            //check for input in text boxes
            if (titleField.getText() == "" || descField.getText() == "" || locationField.getText() == "") {
                errorLabel.setText("Missing input value, please try again");
            } else {
                try {
                    //parse user input and put into variables
                    String newTitle = titleField.getText();
                    String description = descField.getText();
                    String location = locationField.getText();
                    Contact contact = contactBox.getValue();
                    String type = typeBox.getValue().toString();
                    ZonedDateTime start = ZonedDateTime.from(formatter.parse(startBox.getValue()));
                    ZonedDateTime end = ZonedDateTime.from(formatter.parse(endBox.getValue()));
                    Customer customer = customerBox.getValue();
                    User user = userBox.getValue();
                    LocalDate startDate = datePicker.getValue();

                    ZonedDateTime utcStart = start.withZoneSameInstant(ZoneId.of("UTC"));
                    ZonedDateTime utcEnd = end.withZoneSameInstant(ZoneId.of("UTC"));

                    Timestamp startStamp = Timestamp.from(Instant.from(utcStart));
                    Timestamp endStamp = Timestamp.from(Instant.from(utcEnd));

                    //make sure the start time is in the future and the end is after start
                    if (end.compareTo(start) > 0 && start.compareTo(ZonedDateTime.now()) > 0) {
                        boolean valid = true;
                        String overlap = "There is an overlapping appointment, please select a different time";

                        ObservableList<Appointment> appointments = AppointmentDB.getCustomerAppointments(customer.getCustomerID());

                        //loop through all appointments and check for overlaps
                        for (Appointment a : appointments) {

                            if ((a.getStartTime().after(startStamp) || a.getStartTime().equals(startStamp)) &&
                                    (a.getEndTime().before(endStamp) || endStamp.equals(a.getEndTime()))) {
                                errorLabel.setText(overlap);
                                valid = false;
                                break;
                            }

                            if ((startStamp.after(a.getStartTime()) || a.getStartTime().equals(startStamp)) &&
                                    (endStamp.before(a.getEndTime()) || endStamp.equals(a.getEndTime()))) {
                                errorLabel.setText(overlap);
                                valid = false;
                                break;
                            }


                        }

                        //if the appointment is valid insert it
                        if (valid == true) {
                            AppointmentDB.insertAppointment(newTitle, description, location, contact.getContactID(),
                                    type, startStamp, endStamp, customer.getCustomerID(), user.getUserID());
                            tablePopulate();
                            errorLabel.setText("");
                            clear();
                        }
                    } else if (end.compareTo(start) <= 0) {
                        errorLabel.setText("The appointment must start before it ends");
                    } else if (start.compareTo(ZonedDateTime.now()) < 0) {
                        errorLabel.setText("Appointments can't be scheduled in the past");
                    }


                } catch (Exception e) {
                    errorLabel.setText("Invalid input, try again.");
                }
            }


        }
        //if the aid field is populated try to update rather than insert
        else {
            //make sure all text fields are populated
            if (titleField.getText() == "" || descField.getText() == "" || locationField.getText() == "") {
                errorLabel.setText("Missing input value, please try again");
            } else {
                try {
                    //parse user input and put into variables
                    Integer aid = Integer.parseInt(aidField.getText());
                    String newTitle = titleField.getText();
                    String description = descField.getText();
                    String location = locationField.getText();
                    Contact contact = contactBox.getValue();
                    String type = typeBox.getValue().toString();
                    ZonedDateTime start = ZonedDateTime.from(formatter.parse(startBox.getValue()));
                    ZonedDateTime end = ZonedDateTime.from(formatter.parse(endBox.getValue()));
                    Customer customer = customerBox.getValue();
                    User user = userBox.getValue();
                    LocalDate startDate = datePicker.getValue();

                    ZonedDateTime utcStart = start.withZoneSameInstant(ZoneId.of("UTC"));
                    ZonedDateTime utcEnd = end.withZoneSameInstant(ZoneId.of("UTC"));

                    Timestamp startStamp = Timestamp.from(Instant.from(utcStart));
                    Timestamp endStamp = Timestamp.from(Instant.from(utcEnd));

                    //make sure end is after start and start is in the future
                    if (end.compareTo(start) > 0 && start.compareTo(ZonedDateTime.now()) > 0) {
                        boolean valid = true;
                        String overlap = "There is an overlapping appointment, please select a different time";

                        ObservableList<Appointment> appointments = AppointmentDB.getCustomerOtherAppointments(customer.getCustomerID(), aid);

                        //loop through other appointments and make sure no overlaps
                        for (Appointment a : appointments) {

                            if ((a.getStartTime().after(startStamp) || a.getStartTime().equals(startStamp)) &&
                                    (a.getEndTime().before(endStamp) || endStamp.equals(a.getEndTime()))) {
                                errorLabel.setText(overlap);
                                valid = false;
                                break;
                            }

                            if ((startStamp.after(a.getStartTime()) || a.getStartTime().equals(startStamp)) &&
                                    (endStamp.before(a.getEndTime()) || endStamp.equals(a.getEndTime()))) {
                                errorLabel.setText(overlap);
                                valid = false;
                                break;
                            }

                        }

                        //if the appointment is valid update in the database
                        if (valid == true) {
                            AppointmentDB.updateAppointment(aid, newTitle, description, location, contact.getContactID(),
                                    type, startStamp, endStamp, customer.getCustomerID(), user.getUserID());
                            tablePopulate();
                            errorLabel.setText("");
                            clear();
                        }
                    } else if (end.compareTo(start) <= 0) {
                        errorLabel.setText("The appointment must start before it ends");
                    } else if (start.compareTo(ZonedDateTime.now()) < 0) {
                        errorLabel.setText("Appointments can't be scheduled in the past");
                    }


                } catch (Exception e) {
                    errorLabel.setText("Invalid input, try again.");
                }
            }
        }
    }

    /**
     *
     * @param Clear the input fields when the clear button is clicked
     */
    @FXML private void clearButtonClick(ActionEvent event) {
        clear();
    }




    /**
     * load the selected appointment into the input fields
     *
     * @param event update button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void updateButtonClick(ActionEvent event) throws IOException {
        try{
            Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

            aidField.setText(appointment.getAppointmentID().toString());
            titleField.setText(appointment.getTitle());
            descField.setText(appointment.getDescription());
            locationField.setText(appointment.getLocation());
            contactBox.getSelectionModel().select(ContactDB.getThisContact(appointment.getContactID()));
            typeBox.getSelectionModel().select(appointment.getType());
            customerBox.getSelectionModel().select(CustomerDB.getThisCustomer(appointment.getCustomerID()));
            userBox.getSelectionModel().select(UserDB.getThisUser(appointment.getUserID()));
            datePicker.setValue(appointment.getStartTime().toLocalDateTime().atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDate());
            startBox.setValue(appointment.getStartTime().toLocalDateTime().atZone(ZoneId.systemDefault()).format(formatter));
            endBox.setValue(appointment.getEndTime().toLocalDateTime().atZone(ZoneId.systemDefault()).format(formatter));


        }catch(Exception e){
            errorLabel.setText("");
        }

    }

    /**
     * delete selected appointment from the database after displaying a warning
     *
     * @param event delete button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void deleteButtonClick(ActionEvent event) throws IOException {
        Appointment selectedAppointment = null;
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        //make sure appointment is actually selected
        if(selectedAppointment == null){
            errorLabel.setText("Please select a customer before trying to delete");
        }
        //display warning and if they click ok delete appointment
        else{
            confirmation.setHeaderText("Are you sure you would like to delete this appointment?");
            confirmation.setContentText(selectedAppointment.getAppointmentID()+" "+ selectedAppointment.getTitle());
            Optional<ButtonType> option = confirmation.showAndWait();
            if(option.get() == null){
                errorLabel.setText("Please select a button to proceed");
            }
            else if (option.get() == ButtonType.OK){
                AppointmentDB.deleteAppointment(selectedAppointment.getAppointmentID());
                errorLabel.setText("Appointment " +selectedAppointment.getAppointmentID() +" of type "+selectedAppointment.getType() +" deleted");
                clear();
                tablePopulate();
            }
            else{
                errorLabel.setText("Appointment deletion canceled");
            }

        }
    }

    /**
     * Clears the input fields of any data
     */
   private void clear() {
        try{
            aidField.setText("");
            titleField.setText("");
            descField.setText("");
            locationField.setText("");
            contactBox.getSelectionModel().clearSelection();
            contactBox.setValue(null);
            typeBox.getSelectionModel().clearSelection();
            typeBox.setValue(null);
            customerBox.getSelectionModel().clearSelection();
            customerBox.setValue(null);
            userBox.getSelectionModel().clearSelection();
            userBox.setValue(null);
            datePicker.setValue(LocalDate.now());
            startBox.getSelectionModel().clearSelection();
            startBox.setValue(null);
            endBox.getSelectionModel().clearSelection();
            endBox.setValue(null);
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
     * put all the appointment data from the database in the table view
     */

    private void tablePopulate(){
        ObservableList<Appointment> appointments = AppointmentDB.getAllAppointments();

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

    }

    /**
     *
     * @param event when a date is picked populate the time combo boxes with local date times
     */
    @FXML
    private void datePicked(ActionEvent event) {

        if (datePicker.getValue() != null) {
            ZonedDateTime start = ZonedDateTime.of(datePicker.getValue().getYear(), datePicker.getValue().getMonth().getValue(),
                    datePicker.getValue().getDayOfMonth(), 8, 0, 0, 0, ZoneId.of("US/Eastern"));
            ZonedDateTime end = ZonedDateTime.of(datePicker.getValue().getYear(), datePicker.getValue().getMonth().getValue(),
                    datePicker.getValue().getDayOfMonth(), 23, 45, 0, 0, ZoneId.of("US/Eastern"));

            ZonedDateTime userStart = start.withZoneSameInstant(ZoneId.systemDefault());

            ObservableList<String> startTimes = FXCollections.observableArrayList();
            ObservableList<String> endTimes = FXCollections.observableArrayList();

            while (start.isBefore(end.plusSeconds(1))) {
                startTimes.add(userStart.format(formatter));
                endTimes.add(userStart.plusMinutes(15).format(formatter));
                start = start.plusMinutes(15);
                userStart = userStart.plusMinutes(15);
            }

            startBox.setItems(startTimes);
            endBox.setItems(endTimes);


        }
    }


    /**
     * set the table to the initial data and populate all the combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablePopulate();

        ObservableList<Contact> contacts = ContactDB.getAllContacts();
        contactBox.setItems(contacts);

        ObservableList<String> type = FXCollections.observableArrayList();
        type.setAll("Planning Session", "De-Briefing", "Consultation", "Discovery", "Demo");
        typeBox.setItems(type);


        ObservableList<Customer> customers = CustomerDB.getAllCustomers();
        customerBox.setItems(customers);

        ObservableList<User> users = UserDB.getAllUsers();
        userBox.setItems(users);



    }
}
