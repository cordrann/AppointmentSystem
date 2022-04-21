import Database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {
    /**
     * Load the first screen for the application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/LoginScreen.fxml"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    /**
     * main method, initiates and closes database connection
     * @param args
     */

    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));
        JDBC.makeConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
