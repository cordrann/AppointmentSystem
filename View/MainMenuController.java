package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {

    /**
     * go to the customer menu
     * @param event customer menu button clicked
     * @throws IOException throws input/output exceptions
     */
    @FXML private void customerMenuClick(ActionEvent event) throws IOException {
        Parent customerMenuParent;
        customerMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CustomerMenu.FXML")));
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
        appointmentMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AppointmentMenu.FXML")));
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
        appointmentScheduleParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Schedule.FXML")));
        Scene appointmentScheduleScene = new Scene(appointmentScheduleParent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(appointmentScheduleScene);
        app_stage.show();

    }
}
