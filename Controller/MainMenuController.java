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

    Integer thisUserID;

    public void transferUserData(String uName, Integer uid) {
        currentUser.setText(uName);
        thisUserID = uid;
        ObservableList<Appointment> appointments = AppointmentDB.userAppsWithin15(thisUserID);
        for (Appointment b: appointments){
            System.out.println(b.getTitle());
        }
        if(appointments.size() > 0){
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming appointments in next 15 minutes");
            String alertContent = "";
            for(Appointment a:appointments){
                alertContent = alertContent.concat(a.getTitle() + "\n");
            }
            alert.setContentText(alertContent);
            alert.showAndWait();
        }
    }

}
