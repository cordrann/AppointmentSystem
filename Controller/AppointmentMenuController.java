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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.*;
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

    @FXML private ComboBox<LocalTime> startBox;
    @FXML private ComboBox<LocalTime> endBox;


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
    /**
     * add appointment to the database after checking for valid input
     *
     * @param event add button is clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML
    private void saveButtonClick(ActionEvent event) throws IOException {

        if(aidField.getText() == "") {

            if(titleField.getText() == "" || descField.getText()== "" || locationField.getText() == "") {
                errorLabel.setText("Missing input value, please try again");
            }
            else {
                try {
                    //parse user input and put into variables
                    String newTitle = titleField.getText();
                    String description = descField.getText();
                    String location = locationField.getText();
                    Contact contact  = contactBox.getValue();
                    String type = typeBox.getValue().toString();
                    LocalTime start = startBox.getValue();
                    LocalTime end = endBox.getValue();
                    Customer customer = customerBox.getValue();
                    User user = userBox.getValue();



                    //AppointmentDB.insertAppointment(newTitle, description, location, contact.getContactID(),
                      //     type, start, end, customer.getCustomerID(), user.getUserID());

                    //tablePopulate();
                   // errorLabel.setText("");

                } catch (Exception e) {
                    errorLabel.setText("Invalid input, try again.");
                }
            }



        }

       /* else{
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
        }*/
    }

    /*@FXML private void clearButtonClicked(ActionEvent event) {
        clear();
    }*/



    /**
     * update customer in the database after checking for valid input
     *
     * @param event update button is clicked
     * @throws IOException throws input/output exceptions
     */
   /* @FXML
    private void updateButtonClick(ActionEvent event) throws IOException {
        try{
            Appointment customer = customerTable.getSelectionModel().getSelectedItem();

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

    }*/

    /**
     * delete a customer from the database if one is selected
     * after deleting all related appointments
     *
     * @param event delete button is clicked
     * @throws IOException throws input/output exceptions
     */
    /*@FXML
    private void deleteButtonClick(ActionEvent event) throws IOException {
        Appointment selectedCustomer = null;
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            errorLabel.setText("Please select a customer before trying to delete");
        }
        else{
            confirmation.setHeaderText("Are you sure you would like to delete this customer?");
            confirmation.setContentText(selectedCustomer.getCustomerName());
            Optional<ButtonType> option = confirmation.showAndWait();
            if(option.get() == null){
                errorLabel.setText("Please select a button to proceed");
            }
            else if (option.get() ==ButtonType.OK){
                CustomerDB.deleteCustomer(selectedCustomer.getCustomerID());
                errorLabel.setText("Customer deleted");
                clear();
                tablePopulate();
            }
            else{
                errorLabel.setText("Customer deletion canceled");
            }

        }
    }*/

   /* private void clear() {
        try{
            cidField.setText("");
            customerNameField.setText("");
            addressField.setText("");
            postalField.setText("");
            phoneField.setText("");

            stateBox.getSelectionModel().clearSelection();
            countryBox.getSelectionModel().clearSelection();


        }catch(Exception e){
            errorLabel.setText("Clear failed");
        }
    }*/

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

        ZonedDateTime zdt = ZonedDateTime.now();
        ZoneOffset offset = zdt.getOffset();
        Integer estoffset = offset.compareTo(ZoneOffset.of(ZoneId.of("America/New_York")));

        LocalTime start = LocalTime.of(8+estoffset,0);
        LocalTime end = LocalTime.of(21+estoffset,45);

        while(start.isBefore(end.plusSeconds(1))){
            startBox.getItems().add(start);
            endBox.getItems().add(start.plusMinutes(15));
            start = start.plusMinutes(15);
        }


    }
}
