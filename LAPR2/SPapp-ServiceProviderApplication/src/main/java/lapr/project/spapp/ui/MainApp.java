package lapr.project.spapp.ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lapr.project.Utils.Utils;

import java.io.IOException;
import static javafx.application.Application.launch;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/SPMenuWindow.fxml"));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alerta = Utils.createAlert(Alert.AlertType.CONFIRMATION, "Confirm", "Confirm your action", "Are you sure you want to exit the application?");

                    if (alerta.showAndWait().get() == ButtonType.CANCEL) {
                        event.consume();
                    } else {
                        System.exit(0);
                    }
                }
            });
            primaryStage.show();
        } catch (IOException e) {
            Utils.createAlert(Alert.AlertType.ERROR, "ERROR", "Error starting application", "Error opening the initial fxml file.").show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}