package Controller;

import DataAccess.AppointmentDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppointmentReportController implements Initializable {
    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private ComboBox<String> monthBox;

    @FXML
    private Label numberLabel;

    @FXML
    private void generateButtonClick (ActionEvent event){

        String type = typeBox.getValue();
        Integer month = monthBox.getSelectionModel().getSelectedIndex()+1;

        System.out.println(month);

        numberLabel.setText(AppointmentDB.countAppointments(type, month).toString());

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
        ObservableList<String> types = FXCollections.observableArrayList();
        types.setAll("Planning Session", "De-Briefing", "Consultation", "Discovery", "Demo");
        typeBox.setItems(types);

        ObservableList<String> months = FXCollections.observableArrayList();
        months.setAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthBox.setItems(months);


    }
}
