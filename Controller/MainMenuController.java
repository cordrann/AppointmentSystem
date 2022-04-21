/**
 * @author Andrew Stowe
 */

package Controller;

import DataAccess.AppointmentDB;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuController  {
    /**
     * label displaying currently logged-in user
     */
    @FXML Label currentUser;
    /**
     * go to the customer menu
     * @param event customer menu button clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML private void customerMenuClick(ActionEvent event) throws IOException {
        Parent customerMenuParent;
        customerMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerMenu.FXML")));
        Scene customerMenuScene = new Scene(customerMenuParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(customerMenuScene);
        app_stage.show();

    }

    /**
     * go to the appointment menu
     * @param event appointment menu button clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML private void appointmentMenuClick(ActionEvent event) throws IOException{
        Parent appointmentMenuParent;
        appointmentMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentMenu.FXML")));
        Scene appointmentMenuScene  = new Scene(appointmentMenuParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(appointmentMenuScene);
        app_stage.show();

    }

    /**
     * go to the screen for the contact report
     * @param event contact report button clicked
     * @throws IOException
     */
    @FXML private void reportContactClick(ActionEvent event) throws IOException{
        Parent contactReportParent;
        contactReportParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ContactReport.FXML")));
        Scene contactReportScene  = new Scene(contactReportParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(contactReportScene);
        app_stage.show();
    }

    /**
     *go to the appointment view table
     * @param event view appointment button clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML private void appointmentViewClick(ActionEvent event) throws IOException{
        Parent appointmentScheduleParent;
        appointmentScheduleParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Schedule.FXML")));
        Scene appointmentScheduleScene = new Scene(appointmentScheduleParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(appointmentScheduleScene);
        app_stage.show();

    }

    /**
     * Go to the screen for the report about appointments
     * @param event appointment report button clicked
     * @throws IOException
     */
    @FXML private void reportAppointmentClick(ActionEvent event) throws IOException{
        Parent appointmentReportParent;
        appointmentReportParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentReport.FXML")));
        Scene appointmentReportScene = new Scene(appointmentReportParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(appointmentReportScene);
        app_stage.show();
    }

    /**
     * go to the screen for the report about customers
     * @param event customer report button clicked
     * @throws IOException
     */
    @FXML private void reportCustomerClick(ActionEvent event) throws IOException{
        Parent customerReportParent;
        customerReportParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerReport.FXML")));
        Scene customerReportScene = new Scene(customerReportParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(customerReportScene);
        app_stage.show();
    }

    Integer thisUserID;


    /**
     * gets data from the login screen then shows an alert about whether user has appointments within 15 minutes
     * @param uName user name passed from login screen
     * @param uid user id passed from login screen
     */
    public void transferUserData(String uName, Integer uid) {


        currentUser.setText(uName);
        thisUserID = uid;
        ObservableList<Appointment> appointments = AppointmentDB.userAppsWithin15(thisUserID);
        Alert alert = new Alert (Alert.AlertType.INFORMATION);

        if(appointments.size() > 0){

            alert.setTitle("Upcoming appointments in next 15 minutes");
            String alertContent = "";
            for(Appointment a:appointments){
                alertContent = alertContent.concat("ID: "+a.getAppointmentID().toString()+
                        " Start: "+a.getStartTime().toLocalDateTime().toLocalTime() +" End: "+ a.getEndTime().toLocalDateTime().toLocalTime()+"\n");
            }
            alert.setContentText(alertContent);
            alert.showAndWait();
        }
        else{
            alert.setTitle("No upcoming appointments");
            alert.setContentText(uName+" you have no upcoming appointments within 15 minutes");
            alert.showAndWait();
        }
    }

}
