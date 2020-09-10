package lapr.project.spapp.ui.javafxinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lapr.project.Utils.Utils;

import java.io.IOException;

import javafx.fxml.FXML;
import lapr.project.spapp.ui.javafxinterface.uc16.ImportExecutionOrdersWindowController;
import lapr.project.spapp.ui.javafxinterface.uc17.SortRecordsWindowController;
import lapr.project.spapp.ui.javafxinterface.uc18.CheckTimeLineWindowController;

public class ControllerMenuPS {
    @FXML
    private Button btnImportExOrders;
    @FXML
    private Button btnCheckTimeline;
    @FXML
    private Button btnSeeSortRecords;

    @FXML
    public void importExOrdersAction(ActionEvent actionEvent) {
        Scene scene = null;
        try {
            Stage newWindowStage = new Stage();
            newWindowStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uc16/ImportExecutionOrdersWindow.fxml"));
            Parent root = loader.load();
            scene = new Scene(root);
            newWindowStage.setScene(scene);
            ImportExecutionOrdersWindowController nwController = loader.getController();
            nwController.setPreviousWindowStage((Stage) btnSeeSortRecords.getScene().getWindow());
            nwController.setThisWindowStage(newWindowStage);
            nwController.setOnClose();
            newWindowStage.show();
            ((Stage) btnSeeSortRecords.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.createAlert(Alert.AlertType.ERROR, "ERROR", "Error starting window", "Error opening the window fxml file.").show();
        }
    }

    @FXML
    private void checkTimelineAction(ActionEvent event) {
        Scene scene = null;
        try {
            Stage newWindowStage = new Stage();
            newWindowStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uc18/CheckTimeLineWindow.fxml"));
            Parent root = loader.load();
            scene = new Scene(root);
            newWindowStage.setScene(scene);
            CheckTimeLineWindowController nwController = loader.getController();
            nwController.setPreviousWindowStage((Stage) btnSeeSortRecords.getScene().getWindow());
            nwController.setThisWindowStage(newWindowStage);
            nwController.setOnClose();
            if (nwController.validatesEOSize()) {
                newWindowStage.show();
                ((Stage) btnSeeSortRecords.getScene().getWindow()).close();
            } else {
                Utils.createAlert(Alert.AlertType.INFORMATION, "Information", "There are no Execution Orders", "There are no Execution Orders, to import execution orders use the option \"Import Execution Orders\"").showAndWait();
            }
        } catch (IOException e) {
            Utils.createAlert(Alert.AlertType.ERROR, "ERROR", "Error starting window", "Error opening the window fxml file.").show();
        }
    }

    @FXML
    public void seeSortRecordsAction(ActionEvent actionEvent) {
        Scene scene = null;
        try {
            Stage newWindowStage = new Stage();
            newWindowStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uc17/SortRecordsWindow.fxml"));
            Parent root = loader.load();
            scene = new Scene(root);
            newWindowStage.setScene(scene);
            SortRecordsWindowController nwController = loader.getController();
            nwController.setPreviousWindowStage((Stage) btnSeeSortRecords.getScene().getWindow());
            nwController.setThisWindowStage(newWindowStage);
            nwController.setOnClose();
            if (nwController.validatesEOSize()) {
                newWindowStage.show();
                ((Stage) btnSeeSortRecords.getScene().getWindow()).close();
            } else {
                Utils.createAlert(Alert.AlertType.INFORMATION, "Information", "There are no Execution Orders", "There are no Execution Orders, to import execution orders use the option \"Import Execution Orders\"").showAndWait();
            }
        } catch (IOException e) {
            Utils.createAlert(Alert.AlertType.ERROR, "ERROR", "Error starting window", "Error opening the login fxml file.").show();
        }
    }
}
