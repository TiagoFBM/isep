package lapr.project.spapp.ui.javafxinterface.uc16;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lapr.project.Utils.Utils;
import lapr.project.spapp.controller.ImportExecutionOrdersController;
import lapr.project.spapp.model.ExecutionOrder;

/**
 * FXML Controller class
 *
 * @author InêsLopes
 */
public class ImportExecutionOrdersWindowController implements Initializable {

    @FXML
    private Label lblSelectedFile;
    @FXML
    private ListView<ExecutionOrder> lstViewExOrders;

    /**
     * Attached file.
     */
    private File submitedFile;

    private Stage previousWindowStage;
    private Stage thisWindowStage;
    ImportExecutionOrdersController oController;

    private List<ExecutionOrder> leo;

    public void setPreviousWindowStage(Stage stage) {
        previousWindowStage = stage;
    }

    public void setThisWindowStage(Stage stage) {
        thisWindowStage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oController = new ImportExecutionOrdersController();

    }

    public void setOnClose() {
        thisWindowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                previousWindowStage.show();
                thisWindowStage.close();
            }
        });
    }

    /**
     * Submite file with Execution Orders.
     *
     * @param event represents the event that triggered the action
     */
    @FXML
    private void actionImportOrders(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Comma Separated Values Files", "*.csv"), new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"), new FileChooser.ExtensionFilter("Extensible Markup Language Files", "*.xml"));
        submitedFile = chooser.showOpenDialog(null);

        lblSelectedFile.setText(verifySubmitedFile() ? "Submited File: " + submitedFile.getName() : "Submited File: None");
        leo = oController.importExecutionOrders(submitedFile.getAbsolutePath());
        setlstViewExOrders();
    }

    /**
     * Verify if the submited file exists.
     *
     * @return true if file exists and false if not.
     */
    private boolean verifySubmitedFile() {
        if (submitedFile == null) {
            return false;
        }
        return submitedFile.exists();
    }

    /**
     * Submit qualifications.
     * @param actionEvent represents the event that triggered the action
     */
    public void actionSubmit(ActionEvent actionEvent) {
        ButtonType op = Utils.createAlert(Alert.AlertType.CONFIRMATION , "CONFIRM", "Confirm your action", "Are you sure you want to import the presented Execution Orders?").showAndWait().get();
        if (op == ButtonType.OK) {
            oController.setLstProfessionalQualifications(leo);
            actionCancel(actionEvent);
        }
    }

    private void setlstViewExOrders() {
        ObservableList<ExecutionOrder> lst = FXCollections.observableArrayList(leo);
        lstViewExOrders.setItems(lst);
    }


    /**
     * Cancels the window.
     *
     * @param actionEvent represents the event that triggered the action
     */@FXML
    public void actionCancel(ActionEvent actionEvent) {
        previousWindowStage.show();
        thisWindowStage.close();
    }

    @FXML
    /**
     * ????????????????????
     * @param keyEvent
     */
    public void actionKeyPressed(KeyEvent keyEvent) {

    }

}